package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpCompanyRoleMenu;

public interface ErpCompanyRoleMenuMapper {
    int deleteByPrimaryKey(String rolemenuid);

    int insert(ErpCompanyRoleMenu record);

    int insertSelective(ErpCompanyRoleMenu record);

    ErpCompanyRoleMenu selectByPrimaryKey(String rolemenuid);

    int updateByPrimaryKeySelective(ErpCompanyRoleMenu record);

    int updateByPrimaryKey(ErpCompanyRoleMenu record);
}