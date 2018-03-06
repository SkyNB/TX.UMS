package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.BusinessGroup;
import com.lnet.ums.mapper.dao.BusinessGroupDao;
import com.lnet.ums.mapper.dao.mappers.BusinessGroupMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class BusinessGroupDaoImpl implements BusinessGroupDao {
    @Resource
    BusinessGroupMapper businessGroupMapper;

    @Override
    public Boolean exists(String code, String businessGroupId) {
        return businessGroupMapper.exists(code, businessGroupId);
    }

    @Override
    public Integer insert(BusinessGroup businessGroup) {
        return businessGroupMapper.insert(businessGroup);
    }

    @Override
    public Integer update(BusinessGroup businessGroup) {
        return businessGroupMapper.update(businessGroup);
    }

    @Override
    public BusinessGroup getByCode(String code) {
        return businessGroupMapper.getByCode(code);
    }

    @Override
    public List<BusinessGroup> getAll() {
        return businessGroupMapper.getAll();
    }

    @Override
    public List<BusinessGroup> pageList(Map<String, Object> params) {
        return businessGroupMapper.pageList(params);
    }
}
