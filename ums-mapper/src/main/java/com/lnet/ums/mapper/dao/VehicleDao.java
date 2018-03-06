package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.vehicle.vehicleEntity.Vehicle;

import java.util.List;
import java.util.Map;

public interface VehicleDao {
    boolean exists(String vehicleNo, String vehicleId);

    int insert(Vehicle vehicle);

    int batchInsert(List<Vehicle> vehicle);

    int update(Vehicle vehicle);

    Vehicle get(String vehicleId);

    Vehicle getByVehicleNo(String vehicleNo);

    List<Vehicle> getByBranchCode(String branchCode);

    List<Vehicle> getActiveFromBranch(String branchCode);

    List<Vehicle> getIdleFromBranch(String branchCode, String status);

    List<Vehicle> getByBranchCodeAndSiteCode(String branchCode, String siteCode);

    List<Vehicle> getActiveFromSite(String branchCode, String siteCode);

    List<Vehicle> getIdleFromSite(String branchCode, String siteCode, String status);

    int inactivate(String vehicleNo);

    int activate(String vehicleNo);

    List<Vehicle> search(Map<String, Object> map);

    int updateStatus(String vehicleNo, String status);

    Vehicle getByUserId(String userId);
}
