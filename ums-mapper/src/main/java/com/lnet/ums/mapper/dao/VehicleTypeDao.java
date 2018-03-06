package com.lnet.ums.mapper.dao;

import com.lnet.model.ums.vehicle.vehicleEntity.VehicleType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VehicleTypeDao {
    boolean exists(String vehicleTypeId, String name);

    int insert(VehicleType vehicleType);

    int update(VehicleType vehicleType);

    List<VehicleType> findAll();

    VehicleType get(String vehicleTypeId);

    List<VehicleType> search(Map<String, Object> params);
}
