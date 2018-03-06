package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.BusinessType;
import com.lnet.ums.mapper.dao.BusinessTypeDao;
import com.lnet.ums.mapper.dao.mappers.BusinessTypeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class BusinessTypeDaoImpl implements BusinessTypeDao {

    @Resource
    BusinessTypeMapper businessTypeMapper;
    @Override
    public Integer batchInsert(List<BusinessType> businessType) {
        return businessTypeMapper.batchInsert(businessType);
    }

    @Override
    public Integer deleteByProjectCode(String projectCode) {
        return businessTypeMapper.deleteByProjectCode(projectCode);
    }

    @Override
    public List<BusinessType> getByProjectCode(String projectCode) {
        return businessTypeMapper.getByProjectCode(projectCode);
    }
}
