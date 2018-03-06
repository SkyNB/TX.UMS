package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpCompanyDeptPost;

public interface ErpCompanyDeptPostMapper {
    int deleteByPrimaryKey(String postid);

    int insert(ErpCompanyDeptPost record);

    int insertSelective(ErpCompanyDeptPost record);

    ErpCompanyDeptPost selectByPrimaryKey(String postid);

    int updateByPrimaryKeySelective(ErpCompanyDeptPost record);

    int updateByPrimaryKey(ErpCompanyDeptPost record);
}