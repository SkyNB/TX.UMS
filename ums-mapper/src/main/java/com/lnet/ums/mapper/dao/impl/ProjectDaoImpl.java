package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.customer.customerEntity.Project;
import com.lnet.ums.mapper.dao.ProjectDao;
import com.lnet.ums.mapper.dao.mappers.ProjectMapper;
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
public class ProjectDaoImpl implements ProjectDao {

    public static final String redisKey = MD5Utils.md5ForHex("PROJECT_HASH_KEY");
    @Resource
    RedisTemplate redisTemplate;

    @Resource
    ProjectMapper mapper;

    private void putAll() {
        List<Project> projects = mapper.getAll();
        redisTemplate.opsForHash().putAll(redisKey, projects.stream()
                .collect(Collectors.toMap(Project::getProjectId, Function.identity())));
    }

    @Override
    public Boolean exists(String code, String projectId) {
        return mapper.exists(code, projectId);
    }

    @Override
    public Boolean hasProject(String customerCode, String branchCode, String projectId) {
        return mapper.hasProject(customerCode, branchCode, projectId);
    }

    @Override
    public Integer insert(Project project) {
        int result = mapper.insert(project);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, project.getProjectId(), project);
        } else
            this.putAll();
        return result;
    }

    @Override
    public Integer update(Project project) {
        int result = mapper.update(project);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            redisTemplate.opsForHash().put(redisKey, project.getProjectId(), project);
        } else
            this.putAll();
        return result;
    }

    @Override
    public Integer inactivate(String code) {
        int result = mapper.inactivate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            Project project = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, project.getProjectId(), project);
        } else
            this.putAll();
        return result;
    }

    @Override
    public Integer activate(String code) {
        int result = mapper.activate(code);
        if (result > 0 && redisTemplate.opsForHash().size(redisKey) > 0) {
            Project project = mapper.getByCode(code);
            redisTemplate.opsForHash().put(redisKey, project.getProjectId(), project);
        } else
            this.putAll();
        return result;
    }

    @Override
    public List<Project> getAll() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return redisTemplate.opsForHash().values(redisKey);
        }
        this.putAll();
        return mapper.getAll();
    }

    @Override
    public List<Project> getAvailable() {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Project> projects = redisTemplate.opsForHash().values(redisKey);
            return projects.stream().filter(Project::isActive).collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getAvailable();
    }

    @Override
    public List<Project> getBranchAvailable(String branchCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Project> projects = redisTemplate.opsForHash().values(redisKey);
            return projects.stream().filter(project -> branchCode.equals(project.getBranchCode()))
                    .collect(Collectors.toList());
        }
        this.putAll();
        return mapper.getBranchAvailable(branchCode);
    }

    @Override
    public Project get(String projectId) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            return (Project) redisTemplate.opsForHash().get(redisKey, projectId);
        }
        this.putAll();
        return mapper.get(projectId);
    }

    @Override
    public Project getByCode(String code) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Project> projects = redisTemplate.opsForHash().values(redisKey);
            return projects.stream().filter(project -> code.equals(project.getCode()))
                    .findFirst().get();
        }
        this.putAll();
        return mapper.getByCode(code);
    }

    @Override
    public Project getProject(String branchCode, String customerCode) {
        if (redisTemplate.opsForHash().size(redisKey) > 0) {
            List<Project> projects = redisTemplate.opsForHash().values(redisKey);
            return projects.stream().filter(project ->
                    branchCode.equals(project.getBranchCode())
                            && customerCode.equals(project.getCustomerCode()))
                    .findFirst().get();
        }
        this.putAll();
        return mapper.getProject(branchCode, customerCode);
    }

    @Override
    public List<Project> pageList(Map<String, Object> params) {
        return mapper.pageList(params);
    }

    @Override
    public long getAllCount(Map<String, Object> params) {
        return mapper.getAllCount(params);
    }

    @Override
    public List<Project> page(Map<String, Object> params) {
        return mapper.page(params);
    }
}
