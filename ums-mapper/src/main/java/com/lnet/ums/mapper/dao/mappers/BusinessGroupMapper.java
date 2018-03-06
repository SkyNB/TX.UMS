package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.BusinessGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BusinessGroupMapper {
    Boolean exists(@Param("code") String code, @Param("businessGroupId") String businessGroupId);

    Integer insert(BusinessGroup businessGroup);

    Integer update(BusinessGroup businessGroup);

    BusinessGroup getByCode(String code);

    List<BusinessGroup> getAll();

    List<BusinessGroup> pageList(Map<String, Object> params);
}
