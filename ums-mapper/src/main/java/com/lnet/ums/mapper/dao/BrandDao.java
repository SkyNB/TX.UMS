package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.Brand;

import java.util.List;

public interface BrandDao {
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
