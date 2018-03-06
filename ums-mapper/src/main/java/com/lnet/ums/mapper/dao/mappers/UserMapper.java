package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    boolean exists(@Param("username") String username, @Param("userId") String userId);

    int insert(User user);

    int update(User user);

    int deleteById(String userId);

    User get(String userId);

    User getByUsername(String username);

    int updatePassword(@Param("username") String username, @Param("password") String password);

    int resetPassword(@Param("userIds") List<String> userIds, @Param("password") String password);

    List<User> getByOrgCode(String orgCode);

    List<User> findBySiteCode(String siteCode);

    List<User> getAvailable(String systemCode);

    List<User> pageList(Map<String, Object> params);

    int inactivate(List<String> userIds);

    int activate(List<String> userIds);

    List<User> getAll();

    List<User> getByIds(List<String> userIds);

    long getAllCount(Map<String, Object> params);

    List<User> page(Map<String, Object> params);
}
