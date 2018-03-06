package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.customer.customerEntity.ShipAddress;
import com.lnet.ums.mapper.dao.ShipAddressDao;
import com.lnet.ums.mapper.dao.mappers.ShipAddressMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/11/4.
 */

@Repository
public class ShipAddressDaoImpl implements ShipAddressDao {

    @Resource
    RedisTemplate redisTemplate;
    public static final String redisKey = MD5Utils.md5ForHex("SHIP_ADDRESS_HASH_KEY");
    @Resource
    ShipAddressMapper mapper;

    private void putAll() {
        List<ShipAddress> addresses = mapper.getAll();
        redisTemplate.opsForHash().putAll(redisKey, addresses.stream().collect(Collectors.toMap(ShipAddress::getShipAddressId, Function.identity())));
    }

    @Override
    public boolean exists(String code, String shipAddressId) {
        return mapper.exists(code, shipAddressId);
    }

    @Override
    public int insert(ShipAddress shipAddress) {
        int result = mapper.insert(shipAddress);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, shipAddress.getShipAddressId(), shipAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int update(ShipAddress shipAddress) {
        int result = mapper.update(shipAddress);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, shipAddress.getShipAddressId(), shipAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<ShipAddress> getAll() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return redisTemplate.opsForHash().values(redisKey);
        }
        this.putAll();
        return mapper.getAll();
    }

    @Override
    public ShipAddress get(String shipAddressId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (ShipAddress) redisTemplate.opsForHash().get(redisKey, shipAddressId);
        }
        this.putAll();
        return mapper.get(shipAddressId);
    }

    @Override
    public ShipAddress getByCode(String code) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<ShipAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(address -> address.getCode().equals(code))
                    .findFirst().get();
        }
        this.putAll();
        return mapper.getByCode(code);
    }

    @Override
    public List<ShipAddress> getByCarrierCode(String carrierCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<ShipAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(address -> address.getCarrierCode().equals(carrierCode))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getByCarrierCode(carrierCode);
    }

    @Override
    public List<ShipAddress> getByCarrierCodeAndAvailable(String carrierCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<ShipAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(address ->
                    address.getIsActive() &&
                            address.getCarrierCode().equals(carrierCode))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getByCarrierCode(carrierCode);
    }

    @Override
    public int activate(String code) {
        int result = mapper.activate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            ShipAddress shipAddress = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, shipAddress.getShipAddressId(), shipAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int inactivate(String code) {
        int result = mapper.inactivate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            ShipAddress shipAddress = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, shipAddress.getShipAddressId(), shipAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<ShipAddress> pageList(Map<String, Object> params) {
        return mapper.pageList(params);
    }
}
