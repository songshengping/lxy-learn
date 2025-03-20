package com.lxy.learn.service.impl;

import com.lxy.learn.dao.user.UserDao;
import com.lxy.learn.enums.ErrorCode;
import com.lxy.learn.exception.LxyBusinessException;
import com.lxy.learn.model.entity.postgres.SysUserEntity;
import com.lxy.learn.request.user.*;
import com.lxy.learn.service.UserService;
import com.lxy.learn.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 给李晓玉大美女做的测试方法类
 * @author 86185
 * @Description
 * @User JeremySong
 * @Date 2025/3/12 18:44
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Boolean systemUserLogin(SystemUserLogin request) {
        // 参数判断
        if (CommonUtils.isEmpty(request.getUsername()) || CommonUtils.isEmpty(request.getPassword())) {
            throw new LxyBusinessException(ErrorCode.PARAM_EMPTY_ERROR);
        }
        // 参数校验:用户是否存在
        SysUserEntity sysUserEntity = userDao.querySysUserByUsername(request.getUsername());
        if (CommonUtils.isEmpty(sysUserEntity)) {
            // 按道理这里要提示用户:"用户不存在",你想想为什么不这么提示
            throw new LxyBusinessException("用户名或密码错误");
        }
        // 判断密码是否正确
        if (!Objects.equals(sysUserEntity.getPassword(), request.getPassword())) {
            // 按道理这里要提示用户:"密码错误",你想想为什么不这么提示
            throw new LxyBusinessException("用户名或密码错误");
        }
        return true;
    }

    @Override
    public Boolean systemUserAdd(SysUserAddRequest request) {
        return null;
    }

    @Override
    public Boolean systemUserDelete(SysUserDeleteRequest request) {
        return null;
    }

    @Override
    public Boolean systemUserUpdate(SysUserUpdateRequest request) {
        return null;
    }

    @Override
    public Boolean systemUserQueryPage(SysUserQueryPageRequest request) {
        return null;
    }
}
