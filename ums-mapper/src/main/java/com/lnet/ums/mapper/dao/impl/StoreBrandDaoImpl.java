package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.StoreBrand;
import com.lnet.ums.mapper.dao.StoreBrandDao;
import com.lnet.ums.mapper.dao.mappers.StoreBrandMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

@Repository
public class StoreBrandDaoImpl implements StoreBrandDao {
    @Resource
    StoreBrandMapper mapper;

    @Override
    public int insert(StoreBrand storeBrand) {
        return mapper.insert(storeBrand);
    }

    @Override
    public int batchInsert(List<StoreBrand> storeBrands) {
        return mapper.batchInsert(storeBrands);
    }

    @Override
    public int deleteById(String storeBrandId) {
        return mapper.deleteById(storeBrandId);
    }

    @Override
    public int deleteByStoreCodeAndCustomerCode(String storeCode, String customerCode) {
        return mapper.deleteByStoreCodeAndCustomerCode(storeCode, customerCode);
    }

    @Override
    public List<String> getBrandCodes(String customerCode, String storeCode) {
        return mapper.getBrandCodes(customerCode, storeCode);
    }

    @Override
    public List<String> getBrandNames(String customerCode, String storeCode) {
        return mapper.getBrandNames(customerCode, storeCode);
    }

    @Override
    public List<StoreBrand> getByBrandName(String brandName) {
        return mapper.getByBrandName(brandName);
    }
}
