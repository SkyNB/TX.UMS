package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.StoreBrand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreBrandMapper {
    int insert(StoreBrand storeBrand);

    int batchInsert(List<StoreBrand> storeBrands);

    int deleteById(String storeBrandId);

    int deleteByStoreCodeAndCustomerCode(@Param("storeCode") String storeCode, @Param("customerCode") String customerCode);

    List<String> getBrandCodes(@Param("customerCode") String customerCode, @Param("storeCode") String storeCode);

    List<String> getBrandNames(@Param("customerCode") String customerCode, @Param("storeCode") String storeCode);

    List<StoreBrand> getByBrandName(String brandName);
}
