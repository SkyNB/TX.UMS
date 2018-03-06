package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.CargoType;
import com.lnet.ums.mapper.dao.CargoTypeDao;
import com.lnet.ums.mapper.dao.mappers.CargoTypeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class CargoTypeDaoImpl implements CargoTypeDao {

    @Resource
    CargoTypeMapper cargoTypeMapper;

    @Override
    public Integer batchInsert(List<CargoType> cargoType) {
        return cargoTypeMapper.batchInsert(cargoType);
    }

    @Override
    public Integer deleteByProjectCode(String projectCode) {
        return cargoTypeMapper.deleteByProjectCode(projectCode);
    }

    @Override
    public List<CargoType> getByProjectCode(String projectCode) {
        return cargoTypeMapper.getByProjectCode(projectCode);
    }
}
