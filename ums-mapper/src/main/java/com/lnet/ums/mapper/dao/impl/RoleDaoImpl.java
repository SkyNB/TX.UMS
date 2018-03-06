package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.role.Role;
import com.lnet.ums.mapper.dao.RoleDao;
import com.lnet.ums.mapper.dao.mappers.RoleMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    @Resource
    RoleMapper roleMapper;
    @Override
    public boolean exists(String code, String roleId) {
        return roleMapper.exists(code,roleId);
    }

    @Override
    public int insert(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int update(Role role) {
        return roleMapper.update(role);
    }

    @Override
    public int deleteById(String roleId) {
        return roleMapper.deleteById(roleId);
    }

    @Override
    public int batchInsert(List<Role> roleList) {
        return roleMapper.batchInsert(roleList);
    }

    @Override
    public int activate(String code) {
        return roleMapper.activate(code);
    }

    @Override
    public int inactivate(String code) {
        return roleMapper.inactivate(code);
    }

    @Override
    public Role get(String roleId) {
        return roleMapper.get(roleId);
    }

    @Override
    public List<Role> getAll(String systemCode) {
        return roleMapper.getAll(systemCode);
    }

    @Override
    public Role getByCode(String code) {
        return roleMapper.getByCode(code);
    }

    @Override
    public List<Role> getAvailable(String systemCode) {
        return roleMapper.getAvailable(systemCode);
    }

    @Override
    public List<Role> pageList(Map<String, Object> params) {
        return roleMapper.pageList(params);
    }

    @Override
    public long getAllCount(Map<String, Object> params) {
        return roleMapper.getAllCount(params);
    }

    @Override
    public List<Role> page(Map<String, Object> params) {
        return roleMapper.page(params);
    }
}
