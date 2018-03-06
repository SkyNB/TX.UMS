package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.Brand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandMapper {
    Brand get(String brandId);

    List<Brand> getByCustomerCode(String customerCode);

    List<Brand> getByCustomerCodeAndActive(String customerCode);

    int insert(Brand brand);

    int update(Brand brand);

    int deleteById(String brandId);

    int deleteByCustomerCode(String customerCode);

    int batchInsert(List<Brand> brands);

    List<Brand> getAvailable();
}
