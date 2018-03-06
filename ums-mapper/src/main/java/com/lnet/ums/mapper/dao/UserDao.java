package com.lnet.ums.mapper.dao;


import com.lnet.model.ums.user.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    boolean exists(String username, String userId);

    int insert(User user);

    int update(User user);

    int deleteById(String userId);

    User get(String userId);

    User getByUsername(String username);

    int updatePassword(String username, String password);

    int resetPassword(List<String> userIds, String password);

    List<User> getByOrgCode(String orgCode);

    List<User> findBySiteCode(String siteCode);

    List<User> getAvailable(String systemCode);

    List<User> pageList(Map<String, Object> params);

    int inactivate(List<String> userIds);

    int activate(List<String> userIds);

    long getAllCount(Map<String, Object> params);

    List<User> page(Map<String, Object> params);
}
