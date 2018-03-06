package com.lnet.ums.web.utils;

import com.lnet.framework.security.UserPrincipal;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/9.
 */
@Component
public class UserPrincipalImpl implements UserPrincipal {
    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getMobile() {
        return null;
    }

    @Override
    public String getCurrentBranchCode() {
        return null;
    }

    @Override
    public String getCurrentSiteCode() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public Map<String, String> getBindings() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
