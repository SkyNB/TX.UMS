package com.lnet.ums.web.utils;

import com.lnet.framework.util.PasswordStorage;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class PasswordMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String submittedPassword = String.valueOf(((UsernamePasswordToken) token).getPassword());
        String storedCredentials = info.getCredentials().toString();

        return PasswordStorage.verifyPassword(submittedPassword, storedCredentials);
    }

    private String getSubmittedPassword(AuthenticationToken token) {
        return token != null ? token.getCredentials().toString() : null;
    }

    private String getStoredPassword(AuthenticationInfo storedAccountInfo) {
        return storedAccountInfo != null ? storedAccountInfo.getCredentials().toString() : null;
    }
}
