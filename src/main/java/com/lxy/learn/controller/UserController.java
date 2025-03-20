package com.lxy.learn.controller;

import com.lxy.learn.request.user.*;
import com.lxy.learn.response.Response;
import com.lxy.learn.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 给李晓玉大美女做的测试方法类
 * @Description
 * @User JeremySong
 * @Date 2025/3/12 21:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    /**
     * 系统用户登录
     *
     */
    @PostMapping(value = "/login")
    public Response<Boolean> systemUserLogin(@RequestBody SystemUserLogin request) {
        return new Response<>(userService.systemUserLogin(request));
    }

    /**
     * 系统用户新增
     *
     */
    @PostMapping(value = "/add")
    public Response<Boolean> systemUserAdd(@RequestBody SysUserAddRequest request) {
        return new Response<>(userService.systemUserAdd(request));
    }

    /**
     * 系统用户删除
     *
     */
    @PostMapping(value = "/delete")
    public Response<Boolean> systemUserDelete(@RequestBody SysUserDeleteRequest request) {
        return new Response<>(userService.systemUserDelete(request));
    }

    /**
     * 系统用户修改
     *
     */
    @PostMapping(value = "/update")
    public Response<Boolean> systemUserUpdate(@RequestBody SysUserUpdateRequest request) {
        return new Response<>(userService.systemUserUpdate(request));
    }

    /**
     * 系统用户查询
     *
     */
    @PostMapping(value = "/query/page")
    public Response<Boolean> systemUserQueryPage(@RequestBody SysUserQueryPageRequest request) {
        return new Response<>(userService.systemUserQueryPage(request));
    }
}
