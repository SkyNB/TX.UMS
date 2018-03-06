package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpUserDept;

public interface ErpUserDeptMapper {
    int deleteByPrimaryKey(String userdeptid);

    int insert(ErpUserDept record);

    int insertSelective(ErpUserDept record);

    ErpUserDept selectByPrimaryKey(String userdeptid);

    int updateByPrimaryKeySelective(ErpUserDept record);

    int updateByPrimaryKey(ErpUserDept record);
}