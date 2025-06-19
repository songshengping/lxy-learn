package com.lxy.learn.file;

/**
 * @Description TODO
 * @User JeremySong
 * @Date 2025/5/17 19:00
 * @Version 1.0
 */
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FileDownloader {
    // 8KB buffer
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 5000;
    private static final int TIMEOUT_MS = 30000;
    // 并发下载线程数
    private static final int MAX_THREADS = 5;

    public static void main(String[] args) {
        // SFTP下载示例
        downloadViaSftp("sftp.example.com",  22, "username", "password",
                "/remote/sftp/directory", "/local/download/directory");

        // FTP下载示例
        downloadViaFtp("ftp.example.com",  21, "username", "password",
                "/remote/ftp/directory", "/local/download/directory");
    }

    /**
     * 通过SFTP下载文件
     * @param host SFTP服务器地址
     * @param port SFTP端口
     * @param username 用户名
     * @param password 密码
     * @param remoteDir 远程目录
     * @param localDir 本地目录
     */
    public static void downloadViaSftp(String host, int port, String username, String password,
                                       String remoteDir, String localDir) {
        Session session = null;
        ChannelSftp sftpChannel = null;
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        try {
            // 创建JSch会话
            JSch jsch = new JSch();
            session = jsch.getSession(username,  host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking",  "no");
            session.setTimeout(TIMEOUT_MS);
            session.connect();

            // 打开SFTP通道
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

            // 切换到远程目录
            sftpChannel.cd(remoteDir);

            // 获取文件列表
            List<String> filesToDownload = new ArrayList<>();
            for (Object entry : sftpChannel.ls("."))  {
                if (entry instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) entry;
                    if (!lsEntry.getAttrs().isDir())  {
                        filesToDownload.add(lsEntry.getFilename());
                    }
                }
            }

            log.info("Found  {} files to download from SFTP", filesToDownload.size());

            // 创建本地目录
            File localDirectory = new File(localDir);
            if (!localDirectory.exists())  {
                localDirectory.mkdirs();
            }

            // 并发下载文件
            for (String filename : filesToDownload) {
                executor.submit(()  -> {
                    downloadSftpFileWithRetry(sftpChannel, filename, remoteDir, localDir);
                });
            }

            // 等待所有任务完成
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE,  TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                log.error("Download  interrupted", e);
                Thread.currentThread().interrupt();
            }

        } catch (JSchException | SftpException e) {
            log.error("SFTP  error: " + e.getMessage(),  e);
        } finally {
            if (sftpChannel != null) {
                sftpChannel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            executor.shutdownNow();
        }
    }

    /**
     * 带重试机制的SFTP文件下载
     */
    private static void downloadSftpFileWithRetry(ChannelSftp sftpChannel, String filename,
                                                  String remoteDir, String localDir) {
        int retryCount = 0;
        boolean success = false;

        while (retryCount < MAX_RETRIES && !success) {
            try {
                downloadSftpFile(sftpChannel, filename, remoteDir, localDir);
                success = true;
                log.info("Successfully  downloaded: {}", filename);
            } catch (Exception e) {
                retryCount++;
                if (retryCount < MAX_RETRIES) {
                    log.warn("Retry  {}/{} for file {} after error: {}",
                            retryCount, MAX_RETRIES, filename, e.getMessage());
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    log.error("Failed  to download file {} after {} retries: {}",
                            filename, MAX_RETRIES, e.getMessage());
                }
            }
        }
    }

    /**
     * 下载单个SFTP文件
     */
    private static void downloadSftpFile(ChannelSftp sftpChannel, String filename,
                                         String remoteDir, String localDir) throws SftpException, IOException {
        String remotePath = remoteDir + "/" + filename;
        String localPath = localDir + "/" + filename;
        File localFile = new File(localPath);

        // 检查本地文件是否存在且大小匹配
        if (localFile.exists())  {
            SftpATTRS attrs = sftpChannel.stat(remotePath);
            if (localFile.length()  == attrs.getSize())  {
                log.info("File  {} already exists with same size, skipping", filename);
                return;
            }
        }

        log.info("Downloading  {} ({} bytes)", filename, sftpChannel.stat(remotePath).getSize());

        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(localPath))) {
            sftpChannel.get(remotePath,  output, new SftpProgressMonitor() {
                private long totalSize = 0;
                private long transferred = 0;

                @Override
                public void init(int op, String src, String dest, long max) {
                    totalSize = max;
                    log.debug("Starting  download of {} ({} bytes)", src, max);
                }

                @Override
                public boolean count(long count) {
                    transferred += count;
                    if (totalSize > 0) {
                        int percent = (int) (transferred * 100 / totalSize);
                        log.debug("Download  progress: {}% ({} bytes)", percent, transferred);
                    }
                    return true;
                }

                @Override
                public void end() {
                    log.debug("Finished  downloading {} ({} bytes)", filename, transferred);
                }
            });
        }
    }

    /**
     * 通过FTP下载文件
     * @param host FTP服务器地址
     * @param port FTP端口
     * @param username 用户名
     * @param password 密码
     * @param remoteDir 远程目录
     * @param localDir 本地目录
     */
    public static void downloadViaFtp(String host, int port, String username, String password,
                                      String remoteDir, String localDir) {
        FTPClient ftpClient = new FTPClient();
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        try {
            // 连接FTP服务器
            ftpClient.connect(host,  port);
            if (!ftpClient.login(username,  password)) {
                throw new IOException("FTP login failed");
            }

            // 设置传输模式为二进制
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setBufferSize(BUFFER_SIZE);
            ftpClient.setControlKeepAliveTimeout(300);  // 保持连接活跃

            // 切换到远程目录
            if (!ftpClient.changeWorkingDirectory(remoteDir))  {
                throw new IOException("Cannot change to remote directory: " + remoteDir);
            }

            // 获取文件列表
            FTPFile[] files = ftpClient.listFiles();
            List<String> filesToDownload = new ArrayList<>();
            for (FTPFile file : files) {
                if (file.isFile())  {
                    filesToDownload.add(file.getName());
                }
            }

            log.info("Found  {} files to download from FTP", filesToDownload.size());

            // 创建本地目录
            File localDirectory = new File(localDir);
            if (!localDirectory.exists())  {
                localDirectory.mkdirs();
            }

            // 并发下载文件
            for (String filename : filesToDownload) {
                executor.submit(()  -> {
                    downloadFtpFileWithRetry(ftpClient, filename, remoteDir, localDir);
                });
            }

            // 等待所有任务完成
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE,  TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                log.error("Download  interrupted", e);
                Thread.currentThread().interrupt();
            }

        } catch (IOException e) {
            log.error("FTP  error: " + e.getMessage(),  e);
        } finally {
            try {
                if (ftpClient.isConnected())  {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error("Error  disconnecting from FTP", e);
            }
            executor.shutdownNow();
        }
    }

    /**
     * 带重试机制的FTP文件下载
     */
    private static void downloadFtpFileWithRetry(FTPClient ftpClient, String filename,
                                                 String remoteDir, String localDir) {
        int retryCount = 0;
        boolean success = false;

        while (retryCount < MAX_RETRIES && !success) {
            try {
                downloadFtpFile(ftpClient, filename, remoteDir, localDir);
                success = true;
                log.info("Successfully  downloaded: {}", filename);
            } catch (Exception e) {
                retryCount++;
                if (retryCount < MAX_RETRIES) {
                    log.warn("Retry  {}/{} for file {} after error: {}",
                            retryCount, MAX_RETRIES, filename, e.getMessage());
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    log.error("Failed  to download file {} after {} retries: {}",
                            filename, MAX_RETRIES, e.getMessage());
                }
            }
        }
    }

    /**
     * 下载单个FTP文件
     */
    private static void downloadFtpFile(FTPClient ftpClient, String filename,
                                        String remoteDir, String localDir) throws IOException {
        String remotePath = remoteDir + "/" + filename;
        String localPath = localDir + "/" + filename;
        File localFile = new File(localPath);

        // 检查本地文件是否存在且大小匹配
        FTPFile[] remoteFiles = ftpClient.listFiles(remotePath);
        if (remoteFiles.length  == 0) {
            throw new IOException("Remote file not found: " + remotePath);
        }

        long remoteSize = remoteFiles[0].getSize();
        if (localFile.exists())  {
            if (localFile.length()  == remoteSize) {
                log.info("File  {} already exists with same size, skipping", filename);
                return;
            }
        }

        log.info("Downloading  {} ({} bytes)", filename, remoteSize);

        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(localPath))) {
            InputStream input = ftpClient.retrieveFileStream(remotePath);
            if (input == null) {
                throw new IOException("Failed to open input stream for " + remotePath);
            }

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long totalRead = 0;

            while ((bytesRead = input.read(buffer))  != -1) {
                output.write(buffer,  0, bytesRead);
                totalRead += bytesRead;

                if (remoteSize > 0) {
                    int percent = (int) (totalRead * 100 / remoteSize);
                    if (percent % 10 == 0) { // 每10%记录一次进度
                        log.debug("Download  progress: {}% ({} bytes)", percent, totalRead);
                    }
                }
            }

            input.close();
            if (!ftpClient.completePendingCommand())  {
                throw new IOException("Failed to complete pending command");
            }
        }
    }
}
