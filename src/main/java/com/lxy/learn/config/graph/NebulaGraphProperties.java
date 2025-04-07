package com.lxy.learn.config.graph;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author 86185
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/21 13:48
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "nebula")
public class NebulaGraphProperties {
    private List<NebulaAddress> address;
    private String username;
    private String password;
    private boolean reconnect;
    private String space;

    @Data
    public class NebulaAddress {
        private String host;
        private Integer port;
    }
}
