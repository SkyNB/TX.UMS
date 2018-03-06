package com.lnet.ums.mapper.dao;


import com.lnet.ums.contract.model.oauth.OauthUser;
import com.lnet.ums.contract.oauth.Repository;

import java.util.List;

/**
 * @author lxf
 */

public interface UserRepository extends Repository {

    OauthUser findByGuid(String guid);

    void saveUser(OauthUser user);

    void updateUser(OauthUser user);

    OauthUser findByUsername(String username);

    List<OauthUser> findUsersByUsername(String username);
}