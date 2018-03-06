package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectMapper {
    Boolean exists(@Param("code") String code, @Param("projectId") String projectId);

    Boolean hasProject(@Param("customerCode") String customerCode, @Param("branchCode") String branchCode, @Param("projectId") String projectId);

    Integer insert(Project project);

    Integer update(Project project);

    Integer inactivate(String code);

    Integer activate(String code);

    List<Project> getAll();

    List<Project> getAvailable();

    List<Project> getBranchAvailable(String branchCode);

    Project get(String projectId);

    Project getByCode(String code);

    Project getProject(@Param("branchCode") String branchCode, @Param("customerCode") String customerCode);

    List<Project> pageList(Map<String, Object> params);

    long getAllCount(Map<String, Object> params);

    List<Project> page(Map<String, Object> params);
}
