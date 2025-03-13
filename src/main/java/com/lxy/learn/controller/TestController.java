package com.lxy.learn.controller;

import com.lxy.learn.request.system.SystemUserLogin;
import com.lxy.learn.response.Response;
import com.lxy.learn.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 给李晓玉大美女做的测试方法类
 * @Description
 * @User JeremySong
 * @Date 2025/3/12 21:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private TestService testService;

    /**
     * 系统用户登录
     *
     */
    @PostMapping(value = "/login")
    public Response<Boolean> systemUserLogin(@RequestBody SystemUserLogin request) {
        return new Response<>(testService.systemUserLogin(request));
    }
    
}
