package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.vehicle.vehicleEntity.Vehicle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VehicleMapper {
    boolean exists(@Param("vehicleNo") String vehicleNo, @Param("vehicleId") String vehicleId);

    int insert(Vehicle vehicle);

    int batchInsert(List<Vehicle> vehicles);

    int update(Vehicle vehicle);

    Vehicle get(String vehicleId);

    Vehicle getByVehicleNo(String vehicleNo);

    List<Vehicle> getByBranchCode(String branchCode);

    List<Vehicle> getActiveFromBranch(String branchCode);

    List<Vehicle> getIdleFromBranch(@Param("branchCode") String branchCode, @Param("status") String status);

    List<Vehicle> getByBranchCodeAndSiteCode(@Param("branchCode") String branchCode, @Param("siteCode") String siteCode);

    List<Vehicle> getActiveFromSite(@Param("branchCode") String branchCode, @Param("siteCode") String siteCode);

    List<Vehicle> getIdleFromSite(@Param("branchCode") String branchCode, @Param("siteCode") String siteCode, @Param("status") String status);

    int inactivate(String vehicleNo);

    int activate(String vehicleNo);

    List<Vehicle> search(Map<String, Object> map);

    int updateStatus(@Param("vehicleNo") String vehicleNo, @Param("status") String status);

    Vehicle getByUserId(String userId);

    List<Vehicle> findAll();
}
