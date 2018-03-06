package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.Project;

import java.util.List;
import java.util.Map;

public interface ProjectDao {
    Boolean exists(String code, String projectId);

    Boolean hasProject(String customerCode, String branchCode, String projectId);

    Integer insert(Project project);

    Integer update(Project project);

    Integer inactivate(String code);

    Integer activate(String code);

    List<Project> getAll();

    List<Project> getAvailable();

    List<Project> getBranchAvailable(String branchCode);

    Project get(String projectId);

    Project getByCode(String code);

    Project getProject(String branchCode, String customerCode);

    List<Project> pageList(Map<String, Object> params);

    long getAllCount(Map<String, Object> params);

    List<Project> page(Map<String, Object> params);
}
