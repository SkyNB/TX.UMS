package com.lnet.ums.contract.oauth;

import com.lnet.ums.contract.model.oauth.UserFormDto;
import com.lnet.ums.contract.model.oauth.UserJsonDto;
import com.lnet.ums.contract.model.oauth.UserOverviewDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author lxf
 */
public interface OauthUserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();

    UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

    boolean isExistedUsername(String username);

    String saveUser(UserFormDto formDto);
}