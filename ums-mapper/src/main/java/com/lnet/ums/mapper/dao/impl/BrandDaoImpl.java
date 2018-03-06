package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.Brand;
import com.lnet.ums.mapper.dao.BrandDao;
import com.lnet.ums.mapper.dao.mappers.BrandMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class BrandDaoImpl implements BrandDao {
    @Resource
    BrandMapper brandMapper;

    @Override
    public Brand get(String brandId) {
        return brandMapper.get(brandId);
    }

    @Override
    public List<Brand> getByCustomerCode(String customerCode) {
        return brandMapper.getByCustomerCode(customerCode);
    }

    @Override
    public List<Brand> getByCustomerCodeAndActive(String customerCode) {
        return brandMapper.getByCustomerCodeAndActive(customerCode);
    }

    @Override
    public int insert(Brand brand) {
        return brandMapper.insert(brand);
    }

    @Override
    public int update(Brand brand) {
        return brandMapper.update(brand);
    }

    @Override
    public int deleteById(String brandId) {
        return brandMapper.deleteById(brandId);
    }

    @Override
    public int deleteByCustomerCode(String customerCode) {
        return brandMapper.deleteByCustomerCode(customerCode);
    }

    @Override
    public int batchInsert(List<Brand> brands) {
        return brandMapper.batchInsert(brands);
    }

    @Override
    public List<Brand> getAvailable() {
        return brandMapper.getAvailable();
    }
}
