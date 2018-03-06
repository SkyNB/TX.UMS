package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpBaseSystem;

public interface ErpBaseSystemMapper {
    int deleteByPrimaryKey(String systemid);

    int insert(ErpBaseSystem record);

    int insertSelective(ErpBaseSystem record);

    ErpBaseSystem selectByPrimaryKey(String systemid);

    int updateByPrimaryKeySelective(ErpBaseSystem record);

    int updateByPrimaryKey(ErpBaseSystem record);
}