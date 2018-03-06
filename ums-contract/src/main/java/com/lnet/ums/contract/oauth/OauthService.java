package com.lnet.ums.contract.oauth;

import com.lnet.ums.contract.model.oauth.OauthClientDetails;
import com.lnet.ums.contract.model.oauth.OauthClientDetailsDto;

import java.util.List;

/**
 * @author lxf
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OauthClientDetailsDto formDto);
}