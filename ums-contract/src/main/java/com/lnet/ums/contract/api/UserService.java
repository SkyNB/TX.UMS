package com.lnet.ums.contract.api;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.site.Site;
import com.lnet.model.ums.user.User;
import com.lnet.model.ums.user.UserBinding;

import java.util.List;
import java.util.Map;

public interface UserService {
  /*

    Response<String> validateAndGetToken(String userName, String password);
    Response validateToken(String token);*/

    /**
     * 创建用户
     *
     * @param user
     */
    Response<User> create(User user);

    /**
     * 更新用户信息
     *
     * @param user
     */
    Response<User> update(User user);

    /**
     * 删除用户
     *
     * @param userId
     */
    Response delete(String userId);

    /**
     * 根据ID查询单个用户
     *
     * @param userId
     * @return
     */
    Response<User> get(String userId);

    /**
     * 根据用户名查询单个用户
     *
     * @param userName
     * @return
     */
    Response<User> getByUserName(String userName);

    /**
     * 根据系统查询用户
     *
     * @return
     */
    Response<List<User>> getAvailable(String systemCode);

    /**
     * 检查用户是否存在
     *
     * @param userName
     * @return
     */
    boolean exists(String userName);

    /**
     * 修改密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    Response modifyPassword(String username, String oldPassword, String newPassword);

    /**
     * 管理员批量更新密码
     *
     * @param userIds
     * @param newPassword
     */
    Response resetPassword(List<String> userIds, String newPassword);

    /**
     * 根据ID查询用户名
     *
     * @param userId
     * @return
     */
    String getUserName(String userId);

    /**
     * 分页查询
     *
     * @param pageNumber 页数
     * @param pageSize   每页数据量
     * @param params     过滤条件
     * @return
     */
    PageResponse<User> pageList(int pageNumber, int pageSize, Map<String, Object> params);

    /**
     * 绑定用户关联
     *
     * @param userId
     * @param bindingType
     * @param bindingValues
     * @return
     */
    Response bind(String userId, UserBinding.bindingType bindingType, String... bindingValues);

    /**
     * 解绑用户关联
     *
     * @param userId
     * @param bindingType
     * @return
     */
    Response unBind(String userId, UserBinding.bindingType bindingType);

    /**
     * 获取用户的所有绑定
     *
     * @param userId
     * @return
     */
    Response getBindings(String userId);

    /**
     * 获取用户的所有站点
     *
     * @param userId
     * @return
     */
    Response<List<Site>> getAllSite(String userId);

    /**
     * 验证用户
     *
     * @param userName
     * @param password
     * @return
     */
    Response validate(String userName, String password);

    /**
     * 启用
     *
     * @param userIds
     * @return
     */
    Response inactivate(List<String> userIds);

    /**
     * 禁用
     *
     * @param userIds
     * @return
     */
    Response activate(List<String> userIds);

    /**
     * 按组织查找用户列表
     *
     * @param orgCode
     * @return
     */
    ListResponse<User> getByOrgCode(String orgCode);

    /**
     * 按站点查找用户列表
     * @param siteCode
     * @return
     */
    ListResponse<User> findBySiteCode(String siteCode);
}
