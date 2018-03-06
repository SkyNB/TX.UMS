package com.lnet.ums.mapper.dao;


import com.lnet.ums.contract.model.oauth.OauthClientDetails;
import com.lnet.ums.contract.oauth.Repository;

import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author Shengzhao Li
 */
public interface OauthRepository extends Repository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    void updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);
}