package com.lnet.ums.mapper.dao;

import com.lnet.model.ums.role.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao {
    boolean exists(String code, String roleId);

    int insert(Role role);

    int update(Role role);

    int deleteById(String roleId);

    int batchInsert(List<Role> roleList);

    int activate(String code);

    int inactivate(String code);

    Role get(String roleId);

    List<Role> getAll(String systemCode);

    Role getByCode(String code);

    List<Role> getAvailable(String systemCode);

    List<Role> pageList(Map<String, Object> params);

    long getAllCount(Map<String, Object> params);
    List<Role> page(Map<String, Object> params);
}
