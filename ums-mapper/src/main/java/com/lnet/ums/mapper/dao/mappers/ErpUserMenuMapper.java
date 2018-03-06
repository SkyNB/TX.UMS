package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpUserMenu;

public interface ErpUserMenuMapper {
    int deleteByPrimaryKey(String usermenuid);

    int insert(ErpUserMenu record);

    int insertSelective(ErpUserMenu record);

    ErpUserMenu selectByPrimaryKey(String usermenuid);

    int updateByPrimaryKeySelective(ErpUserMenu record);

    int updateByPrimaryKey(ErpUserMenu record);
}