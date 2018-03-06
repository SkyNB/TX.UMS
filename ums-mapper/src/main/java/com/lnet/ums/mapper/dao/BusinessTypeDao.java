package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.BusinessType;

import java.util.List;

public interface BusinessTypeDao {
    Integer batchInsert(List<BusinessType> businessType);

    Integer deleteByProjectCode(String projectCode);

    List<BusinessType> getByProjectCode(String projectCode);
}
