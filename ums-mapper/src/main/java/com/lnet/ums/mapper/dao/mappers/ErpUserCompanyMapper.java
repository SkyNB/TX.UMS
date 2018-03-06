package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpUserCompany;

public interface ErpUserCompanyMapper {
    int deleteByPrimaryKey(String usercompanyid);

    int insert(ErpUserCompany record);

    int insertSelective(ErpUserCompany record);

    ErpUserCompany selectByPrimaryKey(String usercompanyid);

    int updateByPrimaryKeySelective(ErpUserCompany record);

    int updateByPrimaryKey(ErpUserCompany record);
}