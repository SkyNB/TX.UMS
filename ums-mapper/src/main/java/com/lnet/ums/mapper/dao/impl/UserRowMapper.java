/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.lnet.ums.mapper.dao.impl;

import com.lnet.ums.contract.model.oauth.OauthUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2015/11/16
 *
 * @author Shengzhao Li
 */
public class UserRowMapper implements RowMapper<OauthUser> {


    public UserRowMapper() {
    }

    @Override
    public OauthUser mapRow(ResultSet rs, int i) throws SQLException {
        OauthUser user = new OauthUser();

        //user.id(rs.getInt("userId"));
        user.id(rs.getString("userId"));

        user.guid(rs.getString("guid"));

        user.archived(rs.getBoolean("archived"));
        //user.createTime(rs.getTimestamp("create_time").toLocalDateTime());
        user.createTime(rs.getTimestamp("createTime").toLocalDateTime());

        user.email(rs.getString("email"));
        user.phone(rs.getString("phone"));

        //user.password(rs.getString("password"));
        user.password(rs.getString("userPwd"));
        //user.username(rs.getString("username"));
        user.username(rs.getString("userName"));

        //user.lastLoginTime(rs.getTimestamp("last_login_time"));
        user.lastLoginTime(rs.getTimestamp("lastLoginTime"));

        return user;
    }
}
