package com.lxy.learn.service;

import com.lxy.learn.request.user.*;

/**
 * 给李晓玉大美女做的测试方法类
 * @Description
 * @User JeremySong
 * @Date 2025/3/13 13:44
 * @Version 1.0
 */
public interface UserService {

    Boolean systemUserLogin(SystemUserLogin request);

    Boolean systemUserAdd(SysUserAddRequest request);

    Boolean systemUserDelete(SysUserDeleteRequest request);

    Boolean systemUserUpdate(SysUserUpdateRequest request);

    Boolean systemUserQueryPage(SysUserQueryPageRequest request);
}
