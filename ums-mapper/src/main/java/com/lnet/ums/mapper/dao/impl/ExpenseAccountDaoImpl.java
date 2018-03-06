package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.expense.ExpenseAccount;
import com.lnet.ums.mapper.dao.ExpenseAccountDao;
import com.lnet.ums.mapper.dao.mappers.ExpenseAccountMapper;
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
public class ExpenseAccountDaoImpl implements ExpenseAccountDao {

    private static String redisKey = MD5Utils.md5ForHex("ACCOUNT_HASH_KEY");

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    ExpenseAccountMapper expenseAccountMapper;

    private HashOperations getRedisForHash() {
        return redisTemplate.opsForHash();
    }

    private void putAll() {
        List<ExpenseAccount> accounts = expenseAccountMapper.findAll();
        getRedisForHash().putAll(redisKey, accounts.stream()
                .collect(Collectors.toMap(ExpenseAccount::getCode, Function.identity())));
    }

    @Override
    public int create(ExpenseAccount expenseAccount) {
        int result = expenseAccountMapper.create(expenseAccount);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            getRedisForHash().put(redisKey, expenseAccount.getCode(), expenseAccount);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int update(ExpenseAccount expenseAccount) {
        int result = expenseAccountMapper.update(expenseAccount);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            getRedisForHash().put(redisKey, expenseAccount.getCode(), expenseAccount);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int delete(String code) {
        int result = expenseAccountMapper.delete(code);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            getRedisForHash().delete(redisKey, code);
        } else
            this.putAll();
        return result;
    }

    @Override
    public int deleteBySuperCode(String code) {
        int result = expenseAccountMapper.deleteBySuperCode(code);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            List<ExpenseAccount> accounts = getRedisForHash().values(redisKey);
            accounts.stream()
                    .filter(account -> account.getSuperiorCode().equals(code))
                    .forEach(expenseAccount -> getRedisForHash().delete(redisKey, expenseAccount.getCode()));
        } else
            this.putAll();
        return result;
    }

    @Override
    public boolean exists(String expenseAccountId, String code) {
        return expenseAccountMapper.exists(expenseAccountId, code);
    }

    @Override
    public ExpenseAccount get(String expenseAccountId) {
        if (getRedisForHash().size(redisKey) > 0) {
            List<ExpenseAccount> accounts = getRedisForHash().values(redisKey);
            return accounts.stream()
                    .filter(account -> account.getExpenseAccountId().equals(expenseAccountId))
                    .findFirst().get();
        }
        this.putAll();
        return expenseAccountMapper.get(expenseAccountId);
    }

    @Override
    public ExpenseAccount getByCode(String code) {
        if (getRedisForHash().size(redisKey) > 0) {
            return (ExpenseAccount) getRedisForHash().get(redisKey, code);
        }
        this.putAll();
        return expenseAccountMapper.getByCode(code);
    }

    @Override
    public List<ExpenseAccount> pageList(Map<String, Object> params) {
        return expenseAccountMapper.pageList(params);
    }

    @Override
    public List<ExpenseAccount> findBySuperCode(String code) {
        if (getRedisForHash().size(redisKey) > 0) {
            List<ExpenseAccount> accounts = getRedisForHash().values(redisKey);
            List<ExpenseAccount> result = accounts.stream()
                    .filter(account -> account.getSuperiorCode() != null && account.getSuperiorCode().equals(code))
                    .collect(Collectors.toList());
            result.sort((a1, a2) -> a1.getCode().compareTo(a2.getCode()));
            return result;
        }
        this.putAll();
        return expenseAccountMapper.findBySuperCode(code);
    }

    @Override
    public List<ExpenseAccount> findAll() {
        if (getRedisForHash().size(redisKey) > 0) {
            return getRedisForHash().values(redisKey);
        }
        this.putAll();
        return expenseAccountMapper.findAll();
    }
}
