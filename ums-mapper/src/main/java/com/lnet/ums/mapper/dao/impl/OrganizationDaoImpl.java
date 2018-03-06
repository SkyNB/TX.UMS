package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.organization.Organization;
import com.lnet.ums.mapper.dao.OrganizationDao;
import com.lnet.ums.mapper.dao.mappers.OrganizationMapper;
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
public class OrganizationDaoImpl implements OrganizationDao {

    private static String redisKey = MD5Utils.md5ForHex("ORGANIZATION_HASH_KEY");

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    OrganizationMapper organizationMapper;

    private HashOperations getRedisForHash(){
        return redisTemplate.opsForHash();
    }
    private void putAll(){
        List<Organization> organizations = organizationMapper.findAll();
        getRedisForHash().putAll(redisKey,organizations.stream()
                .collect(Collectors.toMap(Organization::getOrganizationId, Function.identity())));
    }
    @Override
    public boolean exists(String code, String organizationId) {
        return organizationMapper.exists(code,organizationId);
    }

    @Override
    public int insert(Organization organization) {
        int result =organizationMapper.insert(organization);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            getRedisForHash().put(redisKey,organization.getOrganizationId(),organization);
        }else
            putAll();
        return result;
    }

    @Override
    public int deleteById(String organizationId) {
        int result =organizationMapper.deleteById(organizationId);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            getRedisForHash().delete(redisKey,organizationId);
        }else
            putAll();
        return result;
    }

    @Override
    public int deleteByParentId(String parentId) {
        int result = organizationMapper.deleteByParentId(parentId);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            List<Organization> organizations = getRedisForHash().values(redisKey);
            organizations.stream()
                    .filter(organization -> organization.getParentId().equals(parentId))
                    .forEach(organization -> getRedisForHash().delete(redisKey, organization.getOrganizationId()));
        } else
            this.putAll();
        return result;
    }

    @Override
    public int update(Organization organization) {
        int result =organizationMapper.update(organization);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            getRedisForHash().put(redisKey,organization.getOrganizationId(),organization);
        }else
            putAll();
        return result;
    }

    @Override
    public int batchDelete(List<String> ids) {
        int result = organizationMapper.batchDelete(ids);
        if (result > 0 && getRedisForHash().size(redisKey) > 0) {
            List<Organization> organizations = getRedisForHash().multiGet(redisKey,ids);
            organizations
                    .forEach(organization -> getRedisForHash().delete(redisKey, organization));
        } else
            this.putAll();
        return result;
    }

    @Override
    public int activate(String code) {
        int result =organizationMapper.activate(code);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            Organization organization = organizationMapper.getByCode(code);
            getRedisForHash().put(redisKey,organization.getOrganizationId(),organization);
        }else
            putAll();
        return result;
    }

    @Override
    public int inactivate(String code) {
        int result =organizationMapper.inactivate(code);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            Organization organization = organizationMapper.getByCode(code);
            getRedisForHash().put(redisKey,organization.getOrganizationId(),organization);
        }else
            putAll();
        return result;
    }

    @Override
    public Organization get(String organizationId) {
        if(getRedisForHash().size(redisKey)>0){
            return (Organization) getRedisForHash().get(redisKey,organizationId);
        }
        this.putAll();
        return organizationMapper.get(organizationId);
    }

    @Override
    public Organization getByCode(String code) {
        if(getRedisForHash().size(redisKey)>0){
            List<Organization> organizations = getRedisForHash().values(redisKey);
            return organizations.stream()
                    .filter(organization -> organization.getCode().equals(code))
                    .findFirst().get();
        }
        this.putAll();
        return organizationMapper.getByCode(code);
    }

    @Override
    public List<Organization> getAvailable() {
        if(getRedisForHash().size(redisKey)>0){
            List<Organization> organizations = getRedisForHash().values(redisKey);
            return organizations.stream()
                    .filter(Organization::isActive)
                    .collect(Collectors.toList());
        }
        this.putAll();
        return organizationMapper.getAvailable();
    }

    @Override
    public List<Organization> getHierarchical(String code) {
        return organizationMapper.getHierarchical(code);
    }

    @Override
    public List<Organization> getAvailableHierarchical(String code) {
        return  organizationMapper.getAvailableHierarchical(code);
    }

    @Override
    public List<Organization> pageList(Map<String, Object> params) {
        return organizationMapper.pageList(params);
    }
}
