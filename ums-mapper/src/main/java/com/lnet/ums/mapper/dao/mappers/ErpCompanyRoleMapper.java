package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpCompanyRole;

public interface ErpCompanyRoleMapper {
    int deleteByPrimaryKey(String companyroleid);

    int insert(ErpCompanyRole record);

    int insertSelective(ErpCompanyRole record);

    ErpCompanyRole selectByPrimaryKey(String companyroleid);

    int updateByPrimaryKeySelective(ErpCompanyRole record);

    int updateByPrimaryKey(ErpCompanyRole record);
}