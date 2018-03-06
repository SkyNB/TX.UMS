package com.lnet.ums.rest.api.Impl;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.model.ums.organization.Organization;
import com.lnet.model.ums.site.Site;
import com.lnet.model.ums.user.User;
import com.lnet.model.ums.user.UserBinding;
import com.lnet.ums.contract.api.OrganizationService;
import com.lnet.ums.contract.api.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/5.
 */
@Component
public class UserApplication {
    @Resource
    UserService userService;
    @Resource
    OrganizationService organizationService;

    public Response resetPassword(List<String> userIds, String password) {
        return userService.resetPassword(userIds, password);
    }

    public Response delete(String userId) {
        return userService.delete(userId);
    }

    public Response create(User model) {
        return userService.create(model);
    }

    public Response edit(User model) {
        return userService.update(model);
    }

    public Response enable(List<String> userIds) {
        return userService.activate(userIds);
    }

    public Response unEnable(List<String> userIds) {
        return userService.inactivate(userIds);
    }

    public Response<User> get(String userId) {
        return userService.get(userId);
    }

    public Object pageList(KendoGridRequest params) {
        return userService.pageList(params.getPage(), params.getPageSize(), params.getParams());
    }

    public Response<List<User>> getAvailable(String sysCode) {
        return userService.getAvailable(sysCode);
    }

    public Response bind(String userId, UserBinding.bindingType bindType, String... bindValue) {
        return userService.bind(userId, bindType, bindValue);
    }

    public Response<Map<String, String>> getBindings(String userId) {
        return userService.getBindings(userId);
    }

    public Response<User> login(String userName, String password) {
        Response<User> userResponse = userService.getByUserName(userName);
        if (userResponse.isSuccess()) {
            User loginUser = userResponse.getBody();
            if (!loginUser.isActive()) {
                return ResponseBuilder.fail("用户已禁用");
            }
            if (!userService.validate(userName, password).isSuccess()) {
                return ResponseBuilder.fail("用户名或密码错误");
            }
        } else {
            return ResponseBuilder.fail("用户名不存在");
        }
        return userResponse;
    }

    public Response<User> getByUserName(String userName) {
        return userService.getByUserName(userName);
    }

    public Response<User> modifyPassword(String userName, String oldPassword, String newPassword) {
        return userService.modifyPassword(userName, oldPassword, newPassword);
    }

    public Response<List<Organization>> getAllBranches(String branchCode) {
        return organizationService.getAllBranches(branchCode);
    }

    public Response<List<Site>> getAllSites(String userId) {
        return userService.getAllSite(userId);
    }

    public ListResponse<User> findBySiteCode(String siteCode) {
        return userService.findBySiteCode(siteCode);
    }
}
