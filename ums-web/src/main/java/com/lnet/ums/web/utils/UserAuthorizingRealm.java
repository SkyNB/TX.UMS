package com.lnet.ums.web.utils;

import com.lnet.framework.core.Response;
import com.lnet.ums.contract.api.UserService;
import com.lnet.model.ums.user.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by LH on 2017/1/3.
 */
public class UserAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 角色，权限信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();

        if (null != user) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            user.getRoleCodes().forEach(info::addRole);

            user.getRoles().forEach(role -> {
                if (role.getPermission() != null) {
                    List<String> permissions = Arrays.asList(role.getPermission().split(","));
                    info.addStringPermissions(permissions);
                }
            });
            return info;
        }
        return null;
    }

    /**
     * 用户信息
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();

        if (!StringUtils.hasText(userName)) throw new UnknownAccountException(); //账号不合法

        Response<User> userResponse = userService.getByUserName(userName);
        if (!userResponse.isSuccess()) throw new UnknownAccountException(); //没找到帐号

        User user = userResponse.getBody();
        if (Boolean.FALSE.equals(user.isActive())) throw new LockedAccountException(); //账号已禁用
        return new SimpleAuthenticationInfo(user, user.getPassword(), userName);
    }
}
