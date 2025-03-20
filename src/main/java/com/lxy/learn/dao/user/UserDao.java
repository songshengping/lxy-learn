package com.lxy.learn.dao.user;

import com.lxy.learn.model.entity.postgres.SysUserEntity;

import java.util.List;

public interface UserDao {
    List<SysUserEntity> querySysUserByQueryModel(UserQueryModel queryModel);

    SysUserEntity querySysUserByUsername(String username);
}
