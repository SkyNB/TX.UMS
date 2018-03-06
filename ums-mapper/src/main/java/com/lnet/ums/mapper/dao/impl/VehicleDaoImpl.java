package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.ums.mapper.dao.VehicleDao;
import com.lnet.ums.mapper.dao.mappers.VehicleMapper;
import com.lnet.model.ums.vehicle.vehicleEntity.Vehicle;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/11/3.
 */
@Repository
public class VehicleDaoImpl implements VehicleDao {

    private static String redisKey = MD5Utils.md5ForHex("VEHICLE_HASH_KEY");
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    VehicleMapper vehicleMapper;


    @Override
    public boolean exists(String vehicleNo, String vehicleId) {
        return vehicleMapper.exists(vehicleNo, vehicleId);
    }

    private void putAll() {
        List<Vehicle> vehicles = vehicleMapper.findAll();
        redisTemplate.opsForHash().putAll(redisKey, vehicles.stream()
                .collect(Collectors.toMap(Vehicle::getVehicleId, Function.identity())));
    }

    @Override
    public int insert(Vehicle vehicle) {
        int result = vehicleMapper.insert(vehicle);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, vehicle.getVehicleId(), vehicle);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int batchInsert(List<Vehicle> vehicles){
        int result = vehicleMapper.batchInsert(vehicles);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().putAll(redisKey, vehicles.stream()
                    .collect(Collectors.toMap(Vehicle::getVehicleId, Function.identity())));
        } else
            this.putAll();
        return result;
    }

    @Override
    public int update(Vehicle vehicle) {
        int result = vehicleMapper.update(vehicle);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, vehicle.getVehicleId(), vehicle);
        } else
            this.putAll();
        return result;
    }

    @Override
    public Vehicle get(String vehicleId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (Vehicle) redisTemplate.opsForHash().get(redisKey, vehicleId);
        }
        this.putAll();
        return vehicleMapper.get(vehicleId);
    }

    @Override
    public Vehicle getByVehicleNo(String vehicleNo) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> vehicleNo.equals(vehicle.getVehicleNo())).findFirst().get();
        }
        this.putAll();
        return vehicleMapper.getByVehicleNo(vehicleNo);
    }

    @Override
    public List<Vehicle> getByBranchCode(String branchCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> branchCode.equals(vehicle.getBranchCode())).collect(Collectors.toList());
        }
        this.putAll();
        return vehicleMapper.getByBranchCode(branchCode);
    }

    @Override
    public List<Vehicle> getActiveFromBranch(String branchCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> vehicle.getIsActive() != null && vehicle.getIsActive()
                    && branchCode.equals(vehicle.getBranchCode())).collect(Collectors.toList());
        }
        this.putAll();
        return vehicleMapper.getByBranchCode(branchCode);
    }

    @Override
    public List<Vehicle> getIdleFromBranch(String branchCode, String status) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> vehicle.getIsActive() != null && vehicle.getIsActive()
                    && branchCode.equals(vehicle.getBranchCode())
                    && status.equals(vehicle.getStatus().name())
            ).collect(Collectors.toList());
        }
        this.putAll();
        return vehicleMapper.getIdleFromBranch(branchCode, status);
    }

    @Override
    public List<Vehicle> getByBranchCodeAndSiteCode(String branchCode, String siteCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> branchCode.equals(vehicle.getBranchCode())
                    && siteCode.equals(vehicle.getSiteCode())
            ).collect(Collectors.toList());
        }
        this.putAll();
        return vehicleMapper.getByBranchCodeAndSiteCode(branchCode, siteCode);
    }

    @Override
    public List<Vehicle> getActiveFromSite(String branchCode, String siteCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> vehicle.getIsActive() != null && vehicle.getIsActive()
                    && branchCode.equals(vehicle.getBranchCode())
                    && siteCode.equals(vehicle.getSiteCode())
            ).collect(Collectors.toList());
        }
        this.putAll();
        return vehicleMapper.getActiveFromSite(branchCode, siteCode);
    }

    @Override
    public List<Vehicle> getIdleFromSite(String branchCode, String siteCode, String status) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> vehicle.getIsActive() != null && vehicle.getIsActive()
                    && branchCode.equals(vehicle.getBranchCode())
                    && status.equals(vehicle.getStatus().name())
                    && siteCode.equals(vehicle.getSiteCode())
            ).collect(Collectors.toList());
        }
        this.putAll();
        return vehicleMapper.getIdleFromSite(branchCode, siteCode, status);
    }

    @Override
    public int inactivate(String vehicleNo) {
        int result = vehicleMapper.inactivate(vehicleNo);
        Vehicle vehicle = vehicleMapper.getByVehicleNo(vehicleNo);
        if (result > 0 && vehicle != null && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, vehicle.getVehicleId(), vehicle);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int activate(String vehicleNo) {
        int result = vehicleMapper.activate(vehicleNo);
        Vehicle vehicle = vehicleMapper.getByVehicleNo(vehicleNo);
        if (result > 0 && vehicle != null && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, vehicle.getVehicleId(), vehicle);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Vehicle> search(Map<String, Object> map) {
        return vehicleMapper.search(map);
    }

    @Override
    public int updateStatus(String vehicleNo, String status) {
        int result = vehicleMapper.updateStatus(vehicleNo, status);
        Vehicle vehicle = vehicleMapper.getByVehicleNo(vehicleNo);
        if (result > 0 && vehicle != null && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, vehicle.getVehicleId(), vehicle);
        } else
            this.putAll();
        return result;
    }

    @Override
    public Vehicle getByUserId(String userId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Vehicle> vehicles = redisTemplate.opsForHash().values(redisKey);
            return vehicles.stream().filter(vehicle -> userId.equals(vehicle.getUserId())).findFirst().get();
        }
        this.putAll();
        return vehicleMapper.getByUserId(userId);
    }
}
