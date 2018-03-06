package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpUserPost;

public interface ErpUserPostMapper {
    int deleteByPrimaryKey(String userdeptid);

    int insert(ErpUserPost record);

    int insertSelective(ErpUserPost record);

    ErpUserPost selectByPrimaryKey(String userdeptid);

    int updateByPrimaryKeySelective(ErpUserPost record);

    int updateByPrimaryKey(ErpUserPost record);
}