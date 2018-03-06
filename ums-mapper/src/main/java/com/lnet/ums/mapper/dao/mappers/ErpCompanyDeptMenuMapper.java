package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpCompanyDeptMenu;

public interface ErpCompanyDeptMenuMapper {
    int deleteByPrimaryKey(String usermenuid);

    int insert(ErpCompanyDeptMenu record);

    int insertSelective(ErpCompanyDeptMenu record);

    ErpCompanyDeptMenu selectByPrimaryKey(String usermenuid);

    int updateByPrimaryKeySelective(ErpCompanyDeptMenu record);

    int updateByPrimaryKey(ErpCompanyDeptMenu record);
}