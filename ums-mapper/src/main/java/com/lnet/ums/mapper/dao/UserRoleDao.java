package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.userrole.UserRole;

import java.util.List;

public interface UserRoleDao {
    int insert(UserRole userRole);

    int batchInsert(List<UserRole> userRoles);

    int batchDeleteByUserId(List<String> userIds);

    int deleteByUserId(String userId);

    int deleteByRoleCode(String roleCode);

    List<String> getRoleCodesByUserId(String userId);
}
