package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.role.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    /**
     * 检查角色是否存在
     *
     * @param code
     * @return
     */
    boolean exists(String code);

    /**
     * 创建角色
     *
     * @param role
     * @throws Exception
     */
    Response<Role> create(Role role);

    /**
     * 更新角色
     *
     * @param role
     * @throws Exception
     */
    Response<Role> update(Role role);

    /**
     * 删除角色
     *
     * @param roleId
     * @throws Exception
     */
    Response delete(String roleId);

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
     * 根据ID查询单个角色
     *
     * @param roleId
     * @return
     */
    Response<Role> get(String roleId);

    /**
     * 根据编码查询单个角色
     *
     * @param code
     * @return
     */
    Response<Role> getByCode(String code);

    /**
     * 查询所有角色
     *
     * @return
     */
    Response<List<Role>> getAll(String systemCode);

    /**
     * 查询可用的角色集合
     *
     * @return
     */
    Response<List<Role>> getAvailable(String systemCode);

    /**
     * 穿件默认角色
     *
     * @return
     */
    Response createDefaultRole(List<Role> roles);

    /**
     * 分页查询
     *
     * @param pageNumber 页数
     * @param pageSize   每页数据量
     * @param params     过滤条件
     * @return
     */
    PageResponse<Role> pageList(int pageNumber, int pageSize, Map<String, Object> params);
}
