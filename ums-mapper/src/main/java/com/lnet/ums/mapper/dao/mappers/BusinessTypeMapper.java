package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.BusinessType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessTypeMapper {
    Integer batchInsert(List<BusinessType> businessType);

    Integer deleteByProjectCode(String projectCode);

    List<BusinessType> getByProjectCode(String projectCode);
}
