package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.CargoType;

import java.util.List;

public interface CargoTypeDao {
    Integer batchInsert(List<CargoType> cargoType);

    Integer deleteByProjectCode(String projectCode);

    List<CargoType> getByProjectCode(String projectCode);
}
