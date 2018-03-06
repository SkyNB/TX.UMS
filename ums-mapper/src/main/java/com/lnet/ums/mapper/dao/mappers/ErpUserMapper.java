package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpUser;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ErpUserMapper {

    /**
     * 根据userId删除用户
     * @param userid
     * @return
     */
    int deleteByPrimaryKey(String userid);

    /**
     * 根据userId逻辑删除用户
     * @param erpUser
     * @return
     */
    int logicDeleteUser(ErpUser erpUser);

    int insert(ErpUser record);

    int insertSelective(ErpUser record);

    ErpUser selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(ErpUser record);

    int updateByPrimaryKey(ErpUser record);

    /**
     * 查找所有用户信息
     * @return
     */
    List<ErpUser> selectUserTen();

    /**
     * 根据登录用户userId获取所能看的用户信息--未完善
     * @return
     */
    List<ErpUser> selectUserAll(String userId);

    /**
     * 根据登录用户userId获取所能看的自定义字段用户信息
     * @return
     */
    List<ErpUser> selectUserCustom(Map map);

    /**
     * 根据用户名和密码查找是否存在用户
     * @param username
     * @param userpwd
     * @return
     */
    ErpUser selectUserBy(@Param("username") String username, @Param("userpwd") String userpwd);

    /**
     * 根据用户信息返回是否存在用户
     * @param erpUser
     * @return
     */
    int selectByUser(ErpUser erpUser);

    /**
     * 根据用户名查找用户
     * @param userpwd
     * @return
     */
    ErpUser selectUserOne(String userpwd);

    /**
     * 根据用户名查找密码
     * @param username
     * @return
     */
    String selectUserPwd(String username,String userpwd);

    /**
     * 根据用户名密码判断用户是否存在
     * @param erpUser
     * @return
     */
    boolean selectByNamePass(ErpUser erpUser);

    /**
     * 根据用户名密码判断用户是否存在
     * @param map
     * @return
     */
    boolean selectByNamePassword(Map map);

    /**
     * 根据用户名密码判断用户是否存在
     * @param map
     * @return
     */
    ErpUser selectErpUser(Map map);

    /**
     * 根据用户名判断用户是否存在
     * @param map
     * @return
     */
    boolean selectByUserName(Map map);

    /**
     * 添加用户
     * @param erpUser
     * @return
     */
    int addUser(ErpUser erpUser);

    /**
     * 添加用户
     * @param erpUser
     * @return
     */
    int addUserMe(ErpUser erpUser);

}