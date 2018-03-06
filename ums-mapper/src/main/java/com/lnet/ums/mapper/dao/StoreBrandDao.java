package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.StoreBrand;

import java.util.List;

public interface StoreBrandDao {
    int insert(StoreBrand storeBrand);

    int batchInsert(List<StoreBrand> storeBrands);

    int deleteById(String storeBrandId);

    int deleteByStoreCodeAndCustomerCode(String storeCode, String customerCode);

    List<String> getBrandCodes(String customerCode, String storeCode);

    List<String> getBrandNames(String customerCode, String storeCode);

    List<StoreBrand> getByBrandName(String brandName);
}
