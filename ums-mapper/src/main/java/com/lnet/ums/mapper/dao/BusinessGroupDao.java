package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.BusinessGroup;

import java.util.List;
import java.util.Map;

public interface BusinessGroupDao {
    Boolean exists(String code, String businessGroupId);

    Integer insert(BusinessGroup businessGroup);

    Integer update(BusinessGroup businessGroup);

    BusinessGroup getByCode(String code);

    List<BusinessGroup> getAll();

    List<BusinessGroup> pageList(Map<String, Object> params);
}
