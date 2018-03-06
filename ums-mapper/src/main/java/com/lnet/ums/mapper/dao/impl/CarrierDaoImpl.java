package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.ums.mapper.dao.CarrierDao;
import com.lnet.ums.mapper.dao.mappers.CarrierMapper;
import com.lnet.model.ums.carrier.carrierEntity.Carrier;
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
public class CarrierDaoImpl implements CarrierDao {

    private static String redisKey = MD5Utils.md5ForHex("CARRIER_HASH_KEY");
    @Resource
    CarrierMapper carrierMapper;

    @Resource
    RedisTemplate redisTemplate;

    private void putAll() {
        List<Carrier> carriers = carrierMapper.getAll();
        redisTemplate.opsForHash().putAll(redisKey, carriers.stream()
                .collect(Collectors.toMap(Carrier::getCarrierId, Function.identity())));
    }

    @Override
    public Boolean exists(String code, String carrierId) {
        return carrierMapper.exists(code, carrierId);
    }

    @Override
    public int insert(Carrier carrier) {
        int result = carrierMapper.insert(carrier);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, carrier.getCarrierId(), carrier);
        }
        this.putAll();
        return result;
    }

    @Override
    public int batchInsert(List<Carrier> carrier) {
        int result = carrierMapper.batchInsert(carrier);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            carrier.forEach(f -> {
                redisTemplate.opsForHash().put(redisKey, f.getCarrierId(), f);
            });
        }
        this.putAll();
        return result;
    }

    @Override
    public int update(Carrier carrier) {
        int result = carrierMapper.update(carrier);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, carrier.getCarrierId(), carrier);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Carrier> getAll() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return redisTemplate.opsForHash().values(redisKey);
        }
        this.putAll();
        return carrierMapper.getAll();
    }

    @Override
    public Carrier get(String carrierId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (Carrier) redisTemplate.opsForHash().get(redisKey, carrierId);
        }
        this.putAll();
        return carrierMapper.get(carrierId);
    }

    @Override
    public Carrier getByCode(String code) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Carrier> carriers = redisTemplate.opsForHash().values(redisKey);
            return carriers.stream()
                    .filter(carrier -> code.equals(carrier.getCode()))
                    .findFirst().get();
        }
        this.putAll();
        return carrierMapper.getByCode(code);
    }

    @Override
    public List<Carrier> getByBranchCode(String branchCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Carrier> carriers = redisTemplate.opsForHash().values(redisKey);
            return carriers.stream()
                    .filter(carrier -> branchCode.equals(carrier.getBranchCode()))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return carrierMapper.getByBranchCode(branchCode);
    }

    @Override
    public List<Carrier> getByBranchCodeAndAvailable(String branchCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Carrier> carriers = redisTemplate.opsForHash().values(redisKey);
            return carriers.stream()
                    .filter(carrier -> carrier.getIsActive() != null && carrier.getIsActive()
                            && branchCode.equals(carrier.getBranchCode()))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return carrierMapper.getByBranchCode(branchCode);
    }

    @Override
    public int inactivate(String code) {
        int result = carrierMapper.inactivate(code);
        Carrier carrier = carrierMapper.getByCode(code);
        if (result > 0 && carrier != null && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, carrier.getCarrierId(), carrier);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int activate(String code) {
        int result = carrierMapper.activate(code);
        Carrier carrier = carrierMapper.getByCode(code);
        if (result > 0 && carrier != null && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, carrier.getCarrierId(), carrier);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Carrier> search(Map<String, Object> params) {
        return carrierMapper.search(params);
    }
}
