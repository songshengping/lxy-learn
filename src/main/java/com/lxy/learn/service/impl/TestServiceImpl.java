package com.lxy.learn.service.impl;

import com.lxy.learn.exception.LxyBusinessException;
import com.lxy.learn.request.system.SystemUserLogin;
import com.lxy.learn.service.TestService;

/**
 * 给李晓玉大美女做的测试方法类
 * @Description
 * @User JeremySong
 * @Date 2025/3/12 18:44
 * @Version 1.0
 */
public class TestServiceImpl implements TestService {
    @Override
    public Boolean systemUserLogin(SystemUserLogin request) {
        if (request.getUsername().equals("admin") && request.getPassword().equals("123456")) {
            return true;
        } else {
            throw new LxyBusinessException("用户名或密码错误");
        }
    }
}
