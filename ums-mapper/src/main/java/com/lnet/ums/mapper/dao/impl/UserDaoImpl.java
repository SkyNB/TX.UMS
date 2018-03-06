package com.lnet.ums.mapper.dao.impl;

import com.lnet.framework.util.MD5Utils;
import com.lnet.model.ums.user.User;
import com.lnet.ums.mapper.dao.UserDao;
import com.lnet.ums.mapper.dao.mappers.UserMapper;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/11/3.
 */
@Repository
public class UserDaoImpl implements UserDao {


    private static String redisKey = MD5Utils.md5ForHex("USER_HASH_KEY");

    @Resource
    RedisTemplate redisTemplate;
    @Resource
    UserMapper userMapper;
    @Override
    public boolean exists(String username, String userId) {
        return userMapper.exists(username,userId);
    }
    private HashOperations getRedisForHash(){
        return redisTemplate.opsForHash();
    }
    private void putAll(){
        List<User> users = userMapper.getAll();
        getRedisForHash().putAll(redisKey,users.stream()
                .collect(Collectors.toMap(User::getUserId, Function.identity())));
    }
    @Override
    public int insert(User user) {
        int result =userMapper.insert(user);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            getRedisForHash().put(redisKey,user.getUserId(),user);
        }else
            putAll();
        return result;
    }

    @Override
    public int update(User user) {
        int result =userMapper.update(user);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            getRedisForHash().put(redisKey,user.getUserId(),user);
        }else
            putAll();
        return result;
    }

    @Override
    public int deleteById(String userId) {
        int result =userMapper.deleteById(userId);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            getRedisForHash().delete(redisKey,userId);
        }else
            putAll();
        return result;
    }

    @Override
    public User get(String userId) {
        if(getRedisForHash().size(redisKey)>0){
            return (User) getRedisForHash().get(redisKey,userId);
        }
        putAll();
        return userMapper.get(userId);
    }

    @Override
    public User getByUsername(String username) {
        /*if(getRedisForHash().size(redisKey)>0){
            List<User> users= getRedisForHash().values(redisKey);
            return users.stream().filter(user -> user.getUserName().equalsIgnoreCase(username)).findFirst().orElse(null);
        }
        putAll();*/
        return userMapper.getByUsername(username);
    }

    @Override
    public int updatePassword(String username, String password) {
        int result = userMapper.updatePassword(username,password);
        if (result>0&&getRedisForHash().size(redisKey)>0){
            User user = userMapper.getByUsername(username);
            getRedisForHash().put(redisKey,user.getUserId(),user);
        }else
            this.putAll();
        return result;
    }

    @Override
    public int resetPassword(List<String> userIds, String password) {
        int result = userMapper.resetPassword(userIds,password);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            List<User> users = getRedisForHash().multiGet(redisKey,userIds);
            getRedisForHash().putAll(redisKey,users.stream()
                    .collect(Collectors.toMap(User::getUserId, Function.identity())));
        }else
            this.putAll();
        return result;
    }

    @Override
    public List<User> getByOrgCode(String orgCode) {
        return userMapper.getByOrgCode(orgCode);
    }

    @Override
    public List<User> findBySiteCode(String siteCode) {
        return userMapper.findBySiteCode(siteCode);
    }

    @Override
    public List<User> getAvailable(String systemCode) {
        return userMapper.getAvailable(systemCode);
    }

    @Override
    public List<User> pageList(Map<String, Object> params) {
        return userMapper.pageList(params);
    }

    @Override
    public int inactivate(List<String> userIds) {
        int result = userMapper.inactivate(userIds);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            List<User> users = userMapper.getByIds(userIds);
            getRedisForHash().putAll(redisKey,users.stream()
                    .collect(Collectors.toMap(User::getUserId, Function.identity())));
        }else
            this.putAll();
        return result;
    }

    @Override
    public int activate(List<String> userIds) {
        int result = userMapper.activate(userIds);
        if(result>0&&getRedisForHash().size(redisKey)>0){
            List<User> users = userMapper.getByIds(userIds);
            getRedisForHash().putAll(redisKey,users.stream()
                    .collect(Collectors.toMap(User::getUserId, Function.identity())));
        }else
            this.putAll();
        return result;
    }

    @Override
    public long getAllCount(Map<String, Object> params) {
        return userMapper.getAllCount(params);
    }

    @Override
    public List<User> page(Map<String, Object> params) {
        return userMapper.page(params);
    }
}
