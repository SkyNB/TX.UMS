package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.userrole.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    int insert(UserRole userRole);

    int batchInsert(List<UserRole> userRoles);

    int batchDeleteByUserId(List<String> userIds);

    int deleteByUserId(String userId);

    int deleteByRoleCode(String roleCode);

    List<String> getRoleCodesByUserId(String userId);
}
