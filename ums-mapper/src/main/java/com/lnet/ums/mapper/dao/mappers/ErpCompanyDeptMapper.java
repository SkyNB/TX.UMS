package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpCompanyDept;

public interface ErpCompanyDeptMapper {
    int deleteByPrimaryKey(String deptid);

    int insert(ErpCompanyDept record);

    int insertSelective(ErpCompanyDept record);

    ErpCompanyDept selectByPrimaryKey(String deptid);

    int updateByPrimaryKeySelective(ErpCompanyDept record);

    int updateByPrimaryKey(ErpCompanyDept record);
}