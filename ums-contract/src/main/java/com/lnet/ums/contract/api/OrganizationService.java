package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.organization.Organization;

import java.util.List;
import java.util.Map;

public interface OrganizationService {

    /**
     * 检查组织代码是否存在
     *
     * @param organizationCode
     * @return
     */
    boolean exists(String organizationCode);

    /**
     * 新增组织
     *
     * @param organization
     * @throws Exception
     */
    Response<Organization> create(Organization organization);

    /**
     * 删除组织
     *
     * @param organizationId
     */
    Response delete(String organizationId);

    /**
     * 更新组织
     *
     * @param organization
     */
    Response<Organization> update(Organization organization);

    /**
     * 启用
     *
     * @param code
     */
    Response activate(String code);

    /**
     * 启用
     *
     * @param codes
     */
    Response activate(String[] codes);

    /**
     * 禁用
     *
     * @param code
     */
    Response inactivate(String code);

    /**
     * 禁用
     *
     * @param codes
     */
    Response inactivate(String[] codes);

    /**
     * 根据ID查询组织
     *
     * @param organizationId
     * @return
     */
    Response<Organization> get(String organizationId);

    /**
     * 根据编码查询组织
     *
     * @param organizationCode
     * @return
     */
    Response<Organization> getByCode(String organizationCode);

    /**
     * 根据编码查询所有组织树数据
     *
     * @param code
     * @return
     */
    Response<List<Organization>> getHierarchical(String code);

    /**
     * 根据编码查询可用组织树数据
     *
     * @param code
     * @return
     */
    Response<List<Organization>> getAvailableHierarchical(String code);

    /**
     * 递归查找所有分公司(只有分公司)
     *
     * @param code
     * @return
     */
    Response<List<Organization>> getAllBranches(String code);

    /**
     * 分页查询
     *
     * @param pageNumber 页数
     * @param pageSize   每页数据量
     * @param params     过滤条件
     * @return
     */
    PageResponse<Organization> pageList(int pageNumber, int pageSize, Map<String, Object> params);
}
