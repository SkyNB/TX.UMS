package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.CargoType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoTypeMapper {
    Integer batchInsert(List<CargoType> cargoType);

    Integer deleteByProjectCode(String projectCode);

    List<CargoType> getByProjectCode(String projectCode);
}
