package com.lxy.learn.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.*;


@Slf4j
public class CommonUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final Random random = new Random();

    private static final String IP_REGEX = "(\\d{1,3}\\.){3}\\d{1,3}";

    private static final String IP_CIDR_REGEX = IP_REGEX + "/\\d{1,2}";

    private static final String IP_RANGE_REGEX = IP_REGEX + "-" + IP_REGEX;

    private static final String IP_FULL_REGEX = "^(" + IP_REGEX + "|" + IP_CIDR_REGEX + "|" + IP_RANGE_REGEX + ")$";
    /**
     * 随机数
     *
     * @param place 定义随机数的位数
     */

    private static final Random rd = new Random();

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || "".equals(obj.toString().trim());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotNull(String str) {
        return str != null;
    }

    public static boolean isNotNull(List list) {
        return list != null;
    }

    public static boolean isNotNull(Map map) {
        return map != null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * 将java异常信息stackTrace转换成字符串
     *
     * @param e 异常信息类
     */
    public static String exceptionStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String returnStr = "";
        if (null != e) {
            e.printStackTrace(pw);
            pw.flush();
            pw.close();
            returnStr = sw.toString();
        }
        return returnStr;
    }

    /**
     * 随机生成指定位数的字符串
     *
     * @param length
     * @return
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static List<String> iPLongToStringList(List<Long> longIpList) {
        if (CommonUtils.isEmpty(longIpList)) {
            return Collections.emptyList();
        }
        List<String> intList = new ArrayList<String>(longIpList.size());
        for (Long str : longIpList) {
            intList.add(iPLongToString(str));
        }
        return intList;
    }

    public static String iPLongToString(Long ipAddress) {
        return ((ipAddress >> 24) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                (ipAddress & 0xFF);
    }

    public static List<Integer> stringToIntegerList(List<String> strList) {
        if (CommonUtils.isEmpty(strList)) {
            return Collections.emptyList();
        }
        List<Integer> intList = new ArrayList<Integer>(strList.size());
        for (String str : strList) {
            intList.add(Integer.valueOf(str));
        }
        return intList;
    }

    public static List<Integer> stringToIntegerList(String[] strList) {
        if (CommonUtils.isEmpty(strList)) {
            return Collections.emptyList();
        }
        List<Integer> intList = new ArrayList<Integer>(strList.length);
        for (String str : strList) {
            intList.add(Integer.valueOf(str));
        }
        return intList;
    }

    public static List<Long> stringToLongList(String[] strList) {
        if (CommonUtils.isEmpty(strList)) {
            return Collections.emptyList();
        }
        List<Long> intList = new ArrayList<Long>(strList.length);
        for (String str : strList) {
            intList.add(Long.valueOf(str));
        }
        return intList;
    }

    public static int getInt(String s, int defval) {
        if (s == null || "".equals(s)) {
            return (defval);
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static int getInt(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getInt(String s, Integer df) {
        if (s == null || "".equals(s)) {
            return df;
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Integer[] getInts(String[] s) {
        if (s == null) {
            return null;
        }
        Integer[] integer = new Integer[s.length];
        for (int i = 0; i < s.length; i++) {
            integer[i] = Integer.parseInt(s[i]);
        }
        return integer;

    }

    public static double getDouble(String s, double defval) {
        if (s == null || "".equals(s)) {
            return (defval);
        }
        try {
            return (Double.parseDouble(s));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

	/*public static Short getShort(String s) {
		if (StringUtil.isNotEmpty(s)) {
			return (Short.parseShort(s));
		} else {
			return null;
		}
	}*/

    public static double getDou(Double s, double defval) {
        if (s == null) {
            return (defval);
        }
        return s;
    }

    public static int getInt(Object object, int defval) {
        if (isEmpty(object)) {
            return (defval);
        }
        try {
            return (Integer.parseInt(object.toString()));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static Integer getInt(Object object) {
        if (isEmpty(object)) {
            return null;
        }
        try {
            return (Integer.parseInt(object.toString()));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int getInt(BigDecimal s, int defval) {
        if (s == null) {
            return (defval);
        }
        return s.intValue();
    }



    public static String getString(String s) {
        return (getString(s, ""));
    }

    public static String getString(Object object) {
        if (isEmpty(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    public static String getString(int i) {
        return (String.valueOf(i));
    }

    public static String getString(float i) {
        return (String.valueOf(i));
    }

    public static String getString(String s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.trim());
    }

    public static String getString(Object s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.toString().trim());
    }


    /**
     * 获取本机IP
     */
    public static String getIp() {
        String ip = null;
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();

        } catch (UnknownHostException e) {
            log.error("发生错误", e);
        }
        return ip;
    }

    /**
     * 判断一个类是否为基本数据类型。
     *
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    private static boolean isBaseDataType(Class clazz) throws Exception {
        return (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz.isPrimitive());
    }
}