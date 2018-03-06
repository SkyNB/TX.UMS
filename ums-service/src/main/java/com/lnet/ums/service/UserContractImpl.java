package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.model.ums.ErpUser;
import com.lnet.ums.contract.api.UserContract;
import com.lnet.model.ums.user.User;
import com.lnet.ums.mapper.dao.mappers.ErpBaseMenuMapper;
import com.lnet.ums.mapper.dao.mappers.ErpUserMapper;
import com.lnet.model.ums.ErpBaseMenu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wanj on 2017/1/13.
 */
@Service
@Transactional
@Slf4j
public class UserContractImpl implements UserContract {

    @Resource
    ErpUserMapper erpUserMapper;

    @Resource
    ErpBaseMenuMapper erpBaseMenuMapper;


    @Override
    public List<ErpUser> selectUserTen() {
        List<ErpUser> listUser = erpUserMapper.selectUserTen();
        return listUser;

    }

    /**
     * 根据登录用户userId获取所能看的自定义字段用户信息
     * @param validateNormal
     * @param validateDisable
     * @return
     */
    @Override
    public List<ErpUser> selectUserCustom(int validateNormal,int validateDisable) {
        Map<String,Integer> userMap = new HashedMap();
        userMap.put("validateNormal",validateNormal);
        userMap.put("validateDisable",validateDisable);
        List<ErpUser> listUserAll = erpUserMapper.selectUserCustom(userMap);
        return listUserAll;
    }

    @Override
    public PageResponse<ErpUser> selectUserCustomList(int pageNumber, int pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNumber, pageSize);
        List<ErpUser> users = erpUserMapper.selectUserCustom(params);
        PageInfo<ErpUser> pageInfo = new PageInfo<>(users);
        return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
    }

    /**
     * 根据登录用户userId获取所能看的用户信息--未完善
     * @param userId
     * @return
     */
    @Override
    public List<ErpUser> selectUserAll(String userId) {
        List<ErpUser> listUserAll = erpUserMapper.selectUserAll(userId);
        return listUserAll;
    }

    @Override
    public ErpUser selectUserBy(String username, String userpwd) {
        ErpUser ErpUser = erpUserMapper.selectUserBy(username,userpwd);
        return ErpUser;
    }

    @Override
    public int selectByUser(ErpUser erpUser) {
        int i = erpUserMapper.selectByUser(erpUser);
        return i;
    }

    @Override
    public ErpUser selectByPrimaryKey(String userid) {
        ErpUser erpUser= erpUserMapper.selectByPrimaryKey(userid);
        return erpUser;
    }

    @Override
    public ErpUser selectUserOne(String username) {
        ErpUser erpUser=erpUserMapper.selectUserOne(username);
        return erpUser;
    }

    @Override
    public String selectUserPwd(String username,String userpwd) {
        String userPwd=erpUserMapper.selectUserPwd(username,userpwd);
        return  userPwd;
    }

    @Override
    public boolean selectByNamePass(ErpUser erpUser) {
        try {
//        boolean stat = false;
            boolean stat = erpUserMapper.selectByNamePass(erpUser);
            System.out.print("################stat#################====" + stat);
            if (stat == true) {
                return stat;
            }
        } catch (Exception e) {
            log.error("er", e);
            System.out.print("################stat#################====" + e.getLocalizedMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean selectByNamePassword(Map map) {
        try {
//        boolean stat = false;
            boolean stat = erpUserMapper.selectByNamePassword(map);
            System.out.print("################stat#################====" + stat);
            if (stat == true) {
                return stat;
            }
        } catch (Exception e) {
            log.error("er", e);
            System.out.print("################stat#################====" + e.getLocalizedMessage());
            return false;
        }
        return false;
    }

    @Override
    public ErpUser selectErpUser(Map map) {
        try {
//        boolean stat = false;
            //根据用户名密码判断用户是否存在
            ErpUser erpUser = erpUserMapper.selectErpUser(map);
            System.out.print("################stat#################====" + erpUser);
            if (erpUser != null) {
                return erpUser;
            }
        } catch (Exception e) {
            log.error("er", e);
            System.out.print("################stat#################====" + e.getLocalizedMessage());
            return null;
        }
        return null;
    }

    /**
     * 根据用户名和密码返回用户信息和资源菜单
     * @param map
     * @return
     */
    @Override
    public Response selectErpUserBase(Map map) {
        Assert.notNull(map);
        Map<String,String> maps = new HashMap<String,String>();
        maps.put("username", map.get("userName").toString());
        maps.put("userpwd",map.get("passWord").toString());
        Response response = new Response();
        //根据用户名密码返回用户信息
        ErpUser erpUser = erpUserMapper.selectErpUser(maps);
        if(erpUser!=null&&erpUser.getUserId()!=null){
            //根据用户id查找所拥有的资源菜单
            List<ErpBaseMenu> erpBaseMenus= erpBaseMenuMapper.selectErpBaseMenuList(erpUser.getUserId());
            if(erpBaseMenus!=null&&erpBaseMenus.size()>0){
                Map<String,Object> rquestMap = new HashMap<String,Object>();
                rquestMap.put("user",erpUser);
                rquestMap.put("base",erpBaseMenus);
                response.setBody(rquestMap);
                response.setSuccess(true);
                response.setMessage("操作成功");
                return response;
            }else {
                response.setSuccess(false);
                response.setMessage("获取用户资源菜单失败");
            }
        }else {
            response.setSuccess(false);
            response.setMessage("查找用户失败");
            return response;
        }
        return response;
    }

    /**
     * 根据用户名判断用户是否存在
     * @param map
     * @return
     */
    @Override
    public boolean selectByUserName(Map map) {
        try {
            boolean stat = erpUserMapper.selectByUserName(map);
            if (stat == true) {
                return stat;
            }
        } catch (Exception e) {
            log.error("er", e.getMessage());
            System.out.print("################stat#################====" + e.getLocalizedMessage());
            return false;
        }
        return false;
    }

    /**
     * 添加用户
     * @param erpUser
     * @return
     */
    @Override
    public boolean addUser(ErpUser erpUser) {
        Assert.notNull(erpUser);
        try {
            if(erpUser==null){
                return false;
            }
            int stat = erpUserMapper.addUserMe(erpUser);
            /*int stat = erpUserMapper.addUser(erpUser);*/
            if (stat != 1) {
                return false;
            }
        } catch (Exception e) {
            log.error("er", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除用户
     * @param userid
     * @return
     */
    @Override
    public boolean deleteByPrimaryKey(String userid) {
        int i = erpUserMapper.deleteByPrimaryKey(userid);
        if(i!=1){
            return false;
        }
        return true;
    }

    /**
     * 根据userId逻辑删除用户
     * @param validate
     * @param userId
     * @return
     */
    @Override
    public boolean logicDeleteUser(int validate,String userId) {
        ErpUser erpUser = new ErpUser();
        erpUser.setUserId(userId);
        erpUser.setValidate(validate);
        int i = erpUserMapper.logicDeleteUser(erpUser);
        if(i!=1){
            return false;
        }
        return true;
    }

}