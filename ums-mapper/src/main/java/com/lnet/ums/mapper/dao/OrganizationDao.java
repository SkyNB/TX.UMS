package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.organization.Organization;

import java.util.List;
import java.util.Map;

public interface OrganizationDao {
    boolean exists(String code, String organizationId);

    int insert(Organization organization);

    int deleteById(String organizationId);

    int deleteByParentId(String parentId);

    int update(Organization organization);

    int batchDelete(List<String> ids);

    int activate(String code);

    int inactivate(String code);

    Organization get(String organizationId);

    Organization getByCode(String code);

    List<Organization> getAvailable();

    List<Organization> getHierarchical(String code);

    List<Organization> getAvailableHierarchical(String code);

    List<Organization> pageList(Map<String, Object> params);
}
