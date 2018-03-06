package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.ErpUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Wanj on 2017/1/13.
 */
public interface UserContract {
    /**
     * 查找所有用户信息
     * @return
     */
/*    List<ErpUserContract> selectAll();*/


    /**
     * 查找10条用户信息
     * @return
     */
    List<ErpUser> selectUserTen();

    /**
     * 根据登录用户userId获取所能看的自定义字段用户信息
     * @return
     */
    List<ErpUser> selectUserCustom(int validateNormal,int validateDisable);

    /**
     * 分页查询
     *
     * @param pageNumber 页数
     * @param pageSize   每页数据量
     * @param params     过滤条件
     * @return
     */
    PageResponse<ErpUser> selectUserCustomList(int pageNumber, int pageSize, Map<String, Object> params);

    /**
     * 根据登录用户userId获取所能看的用户信息--未完善
     * @return
     */
    List<ErpUser> selectUserAll(String userId);

    /**
     * 根据用户名和密码查找是否存在用户
     * @param username
     * @param userpwd
     * @return
     */
    ErpUser selectUserBy(String username,String userpwd);

    /**
     * 根据用户信息查找是否存在用户
     * @param ErpUser
     * @return
     */
    int selectByUser(ErpUser ErpUser);

    /**
     * 根据ID查找用户
     * @param userid
     * @return
     */
    ErpUser selectByPrimaryKey(String userid);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    ErpUser selectUserOne(String username);

    /**
     * 根据用户名查找密码
     * @param username
     * @return
     */
    String selectUserPwd(String username,String userpwd);

    /**
     * 根据用户名密码判断用户是否存在
     * @param ErpUser
     * @return
     */
    boolean selectByNamePass(ErpUser ErpUser);

    /**
     * 根据用户名密码判断用户是否存在
     * @param map
     * @return
     */
    boolean selectByNamePassword(Map map);

    /**
     * 根据用户名密码返回用户信息
     * @param map
     * @return
     */
    ErpUser selectErpUser(Map map);

    /**
     * 根据用户名和密码返回用户信息和资源菜单
     * @param map
     * @return
     */
    Response selectErpUserBase(Map map);

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
    boolean addUser(ErpUser erpUser);

    /**
     * 根据userId删除用户
     * @param userid
     * @return
     */
    boolean deleteByPrimaryKey(String userid);

    /**
     * 根据userId逻辑删除用户
     * @param validate
     * @param userId
     * @return
     */
    boolean logicDeleteUser(int validate,String userId);

}
