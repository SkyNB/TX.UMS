package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.ums.mapper.dao.VehicleTypeDao;
import com.lnet.ums.mapper.dao.mappers.VehicleTypeMapper;
import com.lnet.model.ums.vehicle.vehicleEntity.VehicleType;
import org.springframework.data.redis.core.HashOperations;
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
public class VehicleTypeDaoImpl implements VehicleTypeDao {


    private static String redisKey = MD5Utils.md5ForHex("VEHICLE_TYPE_HASH_KEY");
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    VehicleTypeMapper vehicleTypeMapper;

    private HashOperations getRedisHash() {
        return redisTemplate.opsForHash();
    }

    private void putAll() {
        List<VehicleType> list = vehicleTypeMapper.findAll();
        redisTemplate.opsForHash().putAll(redisKey, list.stream()
                .collect(Collectors.toMap(VehicleType::getVehicleTypeId, Function.identity())));
    }

    @Override
    public boolean exists(String vehicleTypeId, String name) {
        return vehicleTypeMapper.exists(vehicleTypeId, name);
    }

    @Override
    public int insert(VehicleType vehicleType) {
        int result = vehicleTypeMapper.insert(vehicleType);
        if (result > 0 && getRedisHash().size(redisKey) > 0) {
            getRedisHash().put(redisKey, vehicleType.getVehicleTypeId(), vehicleType);
        } else {
            this.putAll();
        }
        return result;
    }

    @Override
    public int update(VehicleType vehicleType) {
        int result = vehicleTypeMapper.update(vehicleType);
        if (result > 0 && getRedisHash().size(redisKey) > 0) {
            getRedisHash().put(redisKey, vehicleType.getVehicleTypeId(), vehicleType);
        } else {
            this.putAll();
        }
        return result;
    }

    @Override
    public List<VehicleType> findAll() {
        if (getRedisHash().size(redisKey) > 0) {
            return getRedisHash().values(redisKey);
        }
        this.putAll();
        return vehicleTypeMapper.findAll();
    }

    @Override
    public VehicleType get(String vehicleTypeId) {
        if (getRedisHash().size(redisKey) > 0) {
            return (VehicleType) getRedisHash().get(redisKey, vehicleTypeId);
        }
        return vehicleTypeMapper.get(vehicleTypeId);
    }

    @Override
    public List<VehicleType> search(Map<String, Object> params) {
        return vehicleTypeMapper.search(params);
    }
}
