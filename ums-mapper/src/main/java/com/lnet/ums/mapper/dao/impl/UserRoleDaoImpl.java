package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.userrole.UserRole;
import com.lnet.ums.mapper.dao.UserRoleDao;
import com.lnet.ums.mapper.dao.mappers.UserRoleMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {

    @Resource
    UserRoleMapper userRoleMapper;

    @Override
    public int insert(UserRole userRole) {
        return userRoleMapper.insert(userRole);
    }

    @Override
    public int batchInsert(List<UserRole> userRoles) {
        return userRoleMapper.batchInsert(userRoles);
    }

    @Override
    public int batchDeleteByUserId(List<String> userIds) {
        return userRoleMapper.batchDeleteByUserId(userIds);
    }

    @Override
    public int deleteByUserId(String userId) {
        return userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public int deleteByRoleCode(String roleCode) {
        return userRoleMapper.deleteByRoleCode(roleCode);
    }

    @Override
    public List<String> getRoleCodesByUserId(String userId) {
        return userRoleMapper.getRoleCodesByUserId(userId);
    }
}
