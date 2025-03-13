package com.lxy.learn.request.system;

import lombok.Data;

/**
 * 用户登录请求
 * @Description
 * @User JeremySong
 * @Date 2025/3/13 14:47
 * @Version 1.0
 */
@Data
public class SystemUserLogin {
    private String username;
    private String password;
}
