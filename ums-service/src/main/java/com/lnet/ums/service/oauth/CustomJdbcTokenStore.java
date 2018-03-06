package com.lnet.ums.service.oauth;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;



/**
 * <p/>
 * 扩展默认的 TokenStore, 增加对缓存的支持
 *
 * @author lxf
 */
public class CustomJdbcTokenStore extends JdbcTokenStore {


    public CustomJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
    }


    @Cacheable(value = "accessTokenCache", key = "#tokenValue")
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return super.readAccessToken(tokenValue);
    }


    @CacheEvict(value = "accessTokenCache", key = "#tokenValue")
    public void removeAccessToken(String tokenValue) {
        super.removeAccessToken(tokenValue);
    }


    @Cacheable(value = "refreshTokenCache", key = "#token")
    public OAuth2RefreshToken readRefreshToken(String token) {
        return super.readRefreshToken(token);
    }

    @CacheEvict(value = "refreshTokenCache", key = "#token")
    public void removeRefreshToken(String token) {
        super.removeRefreshToken(token);
    }


}
