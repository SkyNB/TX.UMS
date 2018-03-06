package com.lnet.ums.web;

import com.lnet.model.ums.ErpUser;
import com.lnet.ums.contract.api.UserContract;
import com.lnet.ums.web.utils.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Wanj on 2017/1/22.
 */
@RequestMapping(value = "xinUser")
@Controller
public class UmsUserController {

    @Resource
    UserContract userContract;

    @RequestMapping(value = "selectUser",method = RequestMethod.GET)
    public String index(ModelMap map,String userId) {

        //根据登录用户userId获取所能看的用户信息
        List<ErpUser> list = userContract.selectUserCustom(ConstantNuoch.CONSTANT_VALIDATE_601,ConstantNuoch.CONSTANT_VALIDATE_602);
        if(list!=null&&list.size()>0){
            map.put("ErpUser", JsonUtils.toJson(list));
        }else{
            return "common/error";
        }
        return "users/index";
    }

    @RequestMapping(value = "adduser",method = {RequestMethod.GET})
    public String aduser(){
        return "umsText/ums";
    }

    /**
     * 添加用户
     * @param map
     * @param erpUser
     * @return
     */
    @RequestMapping(value = "addUser",method = {RequestMethod.POST})
    public String addUserText(ModelMap map, ErpUser erpUser){
        try {
            if(erpUser != null){
                if(erpUser.getUserName()==null&&erpUser.getUserName()==""){
                    map.put("message","用户名不能为空");
                    return "common/error";
                }
                if(erpUser.getUserPwd()==null&&erpUser.getUserPwd()==""){
                    map.put("message","用户密码不能为空");
                    return "common/error";
                }
                if(erpUser.getPhone()==null&&erpUser.getPhone()==""){
                    map.put("message","手机号码不能为空");
                    return "common/error";
                }
                erpUser.setUserId(GetPrimaryKey.getStringPrimaryKey());
                erpUser.setCreateTime(TimeTools.getSqlDate());
                erpUser.setUserPwd(FtpUtils.MD5(erpUser.getUserPwd()));
                erpUser.setCreateUser("xa31202");
                //boolean stat = true;
                boolean stat = userContract.addUser(erpUser);
                if(!stat){
                    map.put("message","添加失败");
                    return "common/error";
                }
            }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
        map.put("message","添加成功");
        return "redirect:selectUser";
    }

    /**
     * 根据用户名判断用户是否存在
     * @param map
     * @param userName
     * @return
     */
    @RequestMapping(value = "selectByUserName",method = {RequestMethod.GET})
    public boolean selectByUserName(ModelMap map,String userName){
        Map<String,String> orMap = new HashedMap();
        if(userName!=null&&userName!=""){
            orMap.put("userName",userName);
            boolean stat=userContract.selectByUserName(orMap);
            if(stat){
                map.put("message","用户名已存在");
                return false;
            }else{
                map.put("message","用户名不能为空");
                return false;
            }
        }
        map.put("message","用户名可用");
        return true;
    }

    /**
     * 逻辑删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public @ResponseBody String deleteUser(ModelMap map,/*@RequestParam(value = "userId", required = true) */String userId){
        boolean stat = userContract.logicDeleteUser(ConstantNuoch.CONSTANT_VALIDATE_603,userId);
        if(!stat){
            return "error";
        }
        return "success";
    }

    /**
     * 修改用户
     * @param map
     * @param userId
     * @return
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public String updateUser(ModelMap map,String userId){
        //boolean stat = userContract.deleteByPrimaryKey(userId);
        //模拟修改失败
        boolean stat = false;
        //模拟修改成功
        //boolean stat = true;
        if(!stat){
            map.put("message","修改失败");
            return "common/error";
        }
        map.put("message","修改成功");
        return "redirect:selectUser";
    }

    /**
     * 停用用户
     * @param map
     * @param userId
     * @return
     */
    @RequestMapping(value = "/disableUser",method = RequestMethod.POST)
    public @ResponseBody String disableUser(ModelMap map,String userId){
        boolean stat = userContract.logicDeleteUser(ConstantNuoch.CONSTANT_VALIDATE_602,userId);
        if(!stat){
            return "error";
        }
        return "success";
    }

    /**
     * 启用用户
     * @param map
     * @param userId
     * @return
     */
    @RequestMapping(value = "/startUser",method = RequestMethod.POST)
    public @ResponseBody String startUser(ModelMap map,String userId){
        boolean stat = userContract.logicDeleteUser(ConstantNuoch.CONSTANT_VALIDATE_601,userId);
        if(!stat){
            return "error";
        }
        return "success";
    }

}
