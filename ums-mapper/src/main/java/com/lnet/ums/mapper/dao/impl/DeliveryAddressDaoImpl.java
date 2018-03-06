package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.customer.customerEntity.DeliveryAddress;
import com.lnet.ums.mapper.dao.DeliveryAddressDao;
import com.lnet.ums.mapper.dao.mappers.DeliveryAddressMapper;
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
public class DeliveryAddressDaoImpl implements DeliveryAddressDao {
    @Resource
    RedisTemplate redisTemplate;
    public static final String redisKey = MD5Utils.md5ForHex("DELIVERY_ADDRESS_HASH_KEY");

    @Resource
    DeliveryAddressMapper mapper;

    private void putAll() {
        List<DeliveryAddress> addresses = mapper.getAll();
        redisTemplate.opsForHash().putAll(redisKey, addresses.stream().collect(Collectors.toMap(DeliveryAddress::getDeliveryAddressId, Function.identity())));
    }

    @Override
    public int insert(DeliveryAddress collectingAddress) {
        int result = mapper.insert(collectingAddress);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getDeliveryAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }


    @Override
    public int update(DeliveryAddress collectingAddress) {
        int result = mapper.update(collectingAddress);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getDeliveryAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<DeliveryAddress> getAll() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return redisTemplate.opsForHash().values(redisKey);
        }
        this.putAll();
        return mapper.getAll();
    }

    @Override
    public List<DeliveryAddress> getAvailable() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<DeliveryAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(DeliveryAddress::isActive)
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getAvailable();
    }

    @Override
    public DeliveryAddress get(String collectingAddressId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (DeliveryAddress) redisTemplate.opsForHash().get(redisKey, collectingAddressId);
        }
        this.putAll();
        return mapper.get(collectingAddressId);
    }


    @Override
    public List<DeliveryAddress> getByOwnerCode(String ownerCode) {

        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<DeliveryAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(address ->
                    address.getOwnerCode().equals(ownerCode))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getByOwnerCode(ownerCode);
    }

    @Override
    public int activate(String deliveryAddressId) {
        int result = mapper.activate(deliveryAddressId);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            DeliveryAddress collectingAddress = mapper.get(deliveryAddressId);
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getDeliveryAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int inactivate(String deliveryAddressId) {
        int result = mapper.inactivate(deliveryAddressId);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            DeliveryAddress collectingAddress = mapper.get(deliveryAddressId);
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getDeliveryAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<DeliveryAddress> pageList(Map<String, Object> params) {
        return mapper.pageList(params);
    }
}
