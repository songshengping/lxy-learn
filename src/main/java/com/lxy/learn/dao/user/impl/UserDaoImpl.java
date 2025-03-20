package com.lxy.learn.dao.user.impl;

import com.lxy.learn.dao.user.UserDao;
import com.lxy.learn.dao.user.UserQueryModel;
import com.lxy.learn.mapper.postgres.mybatis.SysUserMapper;
import com.lxy.learn.model.entity.postgres.SysUserEntity;
import com.lxy.learn.utils.CommonUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUserEntity> querySysUserByQueryModel(UserQueryModel queryModel) {
        if (CommonUtils.isEmpty(queryModel)) {
            return null;
        }
        return sysUserMapper.querySysUserByQueryModel(queryModel);

    }

    @Override
    public SysUserEntity querySysUserByUsername(String username) {
        if (CommonUtils.isEmpty(username)) {
            return null;
        }
        UserQueryModel queryModel = new UserQueryModel();
        queryModel.setUsername(username);
        List<SysUserEntity> sysUserList =  sysUserMapper.querySysUserByQueryModel(queryModel);
        if (CommonUtils.isEmpty(sysUserList)) {
            return null;
        }
        return sysUserList.iterator().next();
    }
}
