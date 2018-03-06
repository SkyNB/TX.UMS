package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.vehicle.vehicleEntity.VehicleType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VehicleTypeMapper {
    boolean exists(@Param("vehicleTypeId") String vehicleTypeId, @Param("name") String name);

    int insert(VehicleType vehicleType);

    int update(VehicleType vehicleType);

    List<VehicleType> findAll();

    VehicleType get(String vehicleTypeId);

    List<VehicleType> search(Map<String, Object> params);
}
