package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.ums.mapper.dao.CustomerDao;
import com.lnet.ums.mapper.dao.mappers.CustomerMapper;
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
public class CustomerDaoImpl implements CustomerDao {

    private static final String redisKey = MD5Utils.md5ForHex("CUSTOMER_HASH_KEY");
    @Resource
    CustomerMapper customerMapper;
    @Resource
    RedisTemplate redisTemplate;

    private void putAll() {
        List<Customer> customers = customerMapper.getAll();
        redisTemplate.opsForHash().putAll(redisKey, customers.stream()
                .collect(Collectors.toMap(Customer::getCustomerId, Function.identity())));
    }

    @Override
    public boolean exists(String code, String rowId) {
        return customerMapper.exists(code, rowId);
    }

    @Override
    public Customer get(String clientId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (Customer) redisTemplate.opsForHash().get(redisKey, clientId);
        }
        this.putAll();
        return customerMapper.get(clientId);
    }

    @Override
    public Customer getByCode(String code) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Customer> customers = redisTemplate.opsForHash().values(redisKey);
            return customers.stream().filter(customer -> customer.getCode().equals(code))
                    .findFirst().get();
        }
        this.putAll();
        return customerMapper.getByCode(code);
    }

    @Override
    public int insert(Customer client) {
        int result = customerMapper.insert(client);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, client.getCustomerId(), client);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int batchInsert(List<Customer> clients) {
        int result = customerMapper.batchInsert(clients);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().putAll(redisKey, clients.stream()
                    .collect(Collectors.toMap(Customer::getCustomerId, Function.identity())));
        } else
            this.putAll();
        return result;
    }

    @Override
    public int update(Customer client) {
        int result = customerMapper.update(client);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, client.getCustomerId(), client);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int activate(String code) {
        int result = customerMapper.activate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            Customer customer = customerMapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, customer.getCustomerId(), customer);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int inactivate(String code) {
        int result = customerMapper.inactivate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            Customer customer = customerMapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, customer.getCustomerId(), customer);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Customer> getAvailable() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Customer> customers = redisTemplate.opsForHash().values(redisKey);
            return customers.stream().filter(Customer::isActive)
                    .collect(Collectors.toList());
        }
        this.putAll();
        return customerMapper.getAvailable();
    }

    @Override
    public List<Customer> getAll() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return redisTemplate.opsForHash().values(redisKey);
        }
        this.putAll();
        return customerMapper.getAll();
    }

    @Override
    public List<Customer> getByCodes(List<String> codes) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Customer> customers = redisTemplate.opsForHash().values(redisKey);
            return customers.stream().filter(customer -> codes.contains(customer.getCode()))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return customerMapper.getByCodes(codes);
    }

    @Override
    public List<Customer> pageList(Map<String, Object> params) {
        return customerMapper.pageList(params);
    }

    @Override
    public int batchInactivate(List<String> ids) {
        int result = customerMapper.batchInactivate(ids);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Customer> customers = redisTemplate.opsForHash().multiGet(redisKey, ids);
            if (customers != null) {
                redisTemplate.opsForHash().putAll(redisKey, customers.stream()
                        .collect(Collectors.toMap(Customer::getCustomerId, Function.identity())));
            }
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Customer> getByBizGroupCode(String bizGroupCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Customer> customers = redisTemplate.opsForHash().values(redisKey);
            return customers.stream().filter(customer -> bizGroupCode.equals(customer.getBizGroupCode()))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return customerMapper.getByBizGroupCode(bizGroupCode);
    }
}
