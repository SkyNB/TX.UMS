package com.lnet.ums.contract.api;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    /**
     * 验证编码是否已存在
     *
     * @param code
     * @param projectId
     * @return
     */
    Boolean exists(String code, String projectId) throws Exception;

    /**
     * 创建项目
     *
     * @param project
     * @return
     */
    Response create(Project project);

    /**
     * 修改项目信息
     *
     * @param project
     * @return
     */
    Response update(Project project);

    /**
     * 禁用项目
     *
     * @param code
     */
    Response inactivate(String code);

    /**
     * 启用项目
     *
     * @param code
     * @return
     */
    Response activate(String code);

    /**
     * 所有项目信息
     *
     * @return
     */
    Response<List<Project>> getAll();

    /**
     * 所有可用的项目信息
     *
     * @return
     */
    Response<List<Project>> getAvailable();

    /**
     * 根据ID查询项目信息
     *
     * @param projectId
     * @return
     */
    Response<Project> get(String projectId);

    /**
     * 根据code查询项目信息
     *
     * @param code
     * @return
     */
    Response<Project> getByCode(String code);

    Response<Project> getProject(String branchCode, String customerCode);

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    PageResponse<Project> pageList(Integer pageNo, Integer pageSize, Map<String, Object> params);

    ListResponse<Project> getBranchAvailable(String branchCode);
}
