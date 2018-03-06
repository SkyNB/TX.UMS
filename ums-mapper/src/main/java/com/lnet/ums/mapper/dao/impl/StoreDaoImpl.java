package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.customer.customerEntity.Store;
import com.lnet.ums.mapper.dao.StoreDao;
import com.lnet.ums.mapper.dao.mappers.StoreMapper;
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
public class StoreDaoImpl implements StoreDao {
    @Resource
    RedisTemplate redisTemplate;
    public static final String redisKey = MD5Utils.md5ForHex("STORE_HASH_KEY");

    @Resource
    StoreMapper mapper;

    private void putAll() {
        List<Store> stores = mapper.getAll();
        redisTemplate.opsForHash().putAll(redisKey, stores.stream().collect(Collectors.toMap(Store::getStoreId, Function.identity())));
    }

    @Override
    public boolean exists(String code, String ownerCode, String storeId) {
        return mapper.exists(code, ownerCode, storeId);
    }

    @Override
    public int insert(Store store) {
        int result = mapper.insert(store);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, store.getStoreId(), store);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int batchInsert(List<Store> stores) {
        int result = mapper.batchInsert(stores);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            stores.forEach(store -> {
                redisTemplate.opsForHash().put(redisKey, store.getStoreId(), store);
            });
        } else
            this.putAll();
        return result;
    }

    @Override
    public int batchDelete(List<String> storeIds) {
        int result = mapper.batchDelete(storeIds);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Store> stores = redisTemplate.opsForHash().multiGet(redisKey, storeIds);
            if (stores != null) {
                stores.forEach(store -> redisTemplate.opsForHash().put(redisKey, store.getStoreId(), store));
            }
        } else
            this.putAll();
        return result;
    }

    @Override
    public int update(Store store) {
        int result = mapper.update(store);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, store.getStoreId(), store);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int deleteById(String storeId) {
        int result = mapper.deleteById(storeId);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            Store store = get(storeId);
            redisTemplate.opsForHash().put(redisKey, store.getStoreId(), store);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Store> getAll() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return redisTemplate.opsForHash().values(redisKey);
        }
        this.putAll();
        return mapper.getAll();
    }

    @Override
    public List<Store> getAvailable() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Store> stores = redisTemplate.opsForHash().values(redisKey);
            return stores.stream().filter(Store::isActive)
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getAvailable();
    }

    @Override
    public List<Store> getBranchAvailable(String branchCode) {
        return mapper.getBranchAvailable(branchCode);
    }

    @Override
    public Store get(String storeId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (Store) redisTemplate.opsForHash().get(redisKey, storeId);
        }
        this.putAll();
        return mapper.get(storeId);
    }

    @Override
    public Store getByCode(String code) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Store> stores = redisTemplate.opsForHash().values(redisKey);
            return stores.stream().filter(store -> store.getCode().equals(code))
                    .findFirst().get();
        }
        this.putAll();
        return mapper.getByCode(code);
    }

    @Override
    public List<Store> getByOwnerCode(String ownerCode) {

        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Store> stores = redisTemplate.opsForHash().values(redisKey);
            return stores.stream().filter(store ->
                    store.getOwnerCode().equals(ownerCode))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getByOwnerCode(ownerCode);
    }

    @Override
    public int activate(String code) {
        int result = mapper.activate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            Store store = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, store.getStoreId(), store);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int inactivate(String code) {
        int result = mapper.inactivate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            Store store = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, store.getStoreId(), store);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Store> pageList(Map<String, Object> params) {
        return mapper.pageList(params);
    }
}
