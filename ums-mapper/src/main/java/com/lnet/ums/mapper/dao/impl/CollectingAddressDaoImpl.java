package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.customer.customerEntity.CollectingAddress;
import com.lnet.ums.mapper.dao.CollectingAddressDao;
import com.lnet.ums.mapper.dao.mappers.CollectingAddressMapper;
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
public class CollectingAddressDaoImpl implements CollectingAddressDao {
    @Resource
    RedisTemplate redisTemplate;
    public static final String redisKey = MD5Utils.md5ForHex("COLLECTING_ADDRESS_HASH_KEY");

    @Resource
    CollectingAddressMapper mapper;

    private void putAll() {
        List<CollectingAddress> addresses = mapper.getAll();
        redisTemplate.opsForHash().putAll(redisKey, addresses.stream().collect(Collectors.toMap(CollectingAddress::getCollectingAddressId, Function.identity())));
    }

    @Override
    public boolean exists(String code, String ownerCode, String collectingAddressId) {
        return mapper.exists(code, ownerCode, collectingAddressId);
    }

    @Override
    public int insert(CollectingAddress collectingAddress) {
        int result = mapper.insert(collectingAddress);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getCollectingAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int batchInsert(List<CollectingAddress> collectingAddresses) {
        int result = mapper.batchInsert(collectingAddresses);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            collectingAddresses.forEach(collectingAddress -> {
                redisTemplate.opsForHash().put(redisKey, collectingAddress.getCollectingAddressId(), collectingAddress);
            });
        } else
            this.putAll();
        return result;
    }

    @Override
    public int update(CollectingAddress collectingAddress) {
        int result = mapper.update(collectingAddress);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getCollectingAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<CollectingAddress> getAll() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return redisTemplate.opsForHash().values(redisKey);
        }
        this.putAll();
        return mapper.getAll();
    }

    @Override
    public List<CollectingAddress> getAvailable() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<CollectingAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(CollectingAddress::isActive)
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getAvailable();
    }

    @Override
    public CollectingAddress get(String collectingAddressId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (CollectingAddress) redisTemplate.opsForHash().get(redisKey, collectingAddressId);
        }
        this.putAll();
        return mapper.get(collectingAddressId);
    }

    @Override
    public CollectingAddress getByCode(String code) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<CollectingAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(address -> address.getCode().equals(code))
                    .findFirst().get();
        }
        this.putAll();
        return mapper.getByCode(code);
    }

    @Override
    public List<CollectingAddress> getByOwnerCode(String ownerCode) {

        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<CollectingAddress> addresses = redisTemplate.opsForHash().values(redisKey);
            return addresses.stream().filter(address ->
                    address.getOwnerCode().equals(ownerCode))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getByOwnerCode(ownerCode);
    }

    @Override
    public int activate(String code) {
        int result = mapper.activate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            CollectingAddress collectingAddress = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getCollectingAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int inactivate(String code) {
        int result = mapper.inactivate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            CollectingAddress collectingAddress = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, collectingAddress.getCollectingAddressId(), collectingAddress);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<CollectingAddress> pageList(Map<String, Object> params) {
        return mapper.pageList(params);
    }
}
