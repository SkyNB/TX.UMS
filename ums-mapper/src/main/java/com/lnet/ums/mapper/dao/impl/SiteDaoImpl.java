package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.site.Site;
import com.lnet.ums.mapper.dao.SiteDao;
import com.lnet.ums.mapper.dao.mappers.SiteMapper;
import org.springframework.data.redis.core.HashOperations;
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
public class SiteDaoImpl implements SiteDao {
    private static String redisKey = MD5Utils.md5ForHex("SITE_HASH_KEY");

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SiteMapper siteMapper;

    private HashOperations getRedisForHash() {
        return redisTemplate.opsForHash();
    }

    private void putAll() {
        List<Site> sites = siteMapper.findAll();
        redisTemplate.opsForHash().putAll(redisKey, sites.stream()
                .collect(Collectors.toMap(Site::getCode, Function.identity())));
    }

    @Override
    public boolean exists(String code, String roleId) {
        return siteMapper.exists(code, roleId);
    }

    @Override
    public int insert(Site record) {
        int result = siteMapper.insert(record);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            getRedisForHash().put(redisKey, record.getCode(), record);
        } else
            putAll();
        return result;
    }

    @Override
    public int update(Site record) {
        int result = siteMapper.update(record);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            getRedisForHash().put(redisKey, record.getCode(), record);
        } else
            putAll();
        return result;
    }

    @Override
    public int deleteById(String siteId) {
        int result = siteMapper.deleteById(siteId);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            Site site = get(siteId);
            if (site != null)
                getRedisForHash().delete(redisKey, site.getCode());
        } else
            putAll();
        return result;
    }

    @Override
    public Site get(String siteId) {
        if (getRedisForHash().size(redisKey) > 0) {
            List<Site> sites = getRedisForHash().values(redisKey);
            return sites.stream()
                    .filter(site -> site.getSiteId().equals(siteId))
                    .findFirst().get();
        }
        this.putAll();
        return siteMapper.get(siteId);
    }

    @Override
    public Site getByCode(String code) {
        if (getRedisForHash().size(redisKey) > 0) {
            return (Site) getRedisForHash().get(redisKey, code);
        }
        this.putAll();
        return siteMapper.getByCode(code);
    }

    @Override
    public List<Site> getByBranchCode(String branchCode) {
        if (getRedisForHash().size(redisKey) > 0) {
            List<Site> sites = getRedisForHash().values(redisKey);
            return sites.stream()
                    .filter(site -> site.getBranchCode().equals(branchCode))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return siteMapper.getByBranchCode(branchCode);
    }

    @Override
    public List<Site> pageList(Map<String, Object> params) {
        return siteMapper.pageList(params);
    }

    @Override
    public List<Site> findByAddressBranch(String districtCode, String branchCode) {
        if (getRedisForHash().size(redisKey) > 0) {
            List<Site> sites = getRedisForHash().values(redisKey);
            return sites.stream()
                    .filter(site -> districtCode.equals(site.getDistrictCode()) && site.getBranchCode().equals(branchCode))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return siteMapper.findByAddressBranch(districtCode, branchCode);
    }

    @Override
    public List<Site> findAll() {
        if (getRedisForHash().size(redisKey) > 0) {
            return getRedisForHash().values(redisKey);
        }
        this.putAll();
        return siteMapper.findAll();
    }
}
