package com.lnet.ums.web;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.ErpUser;
import com.lnet.ums.contract.api.ErpBaseMenuService;
import com.lnet.ums.contract.api.UserContract;
import com.lnet.model.ums.po.ErpBaseMenuCtPo;
import com.lnet.ums.web.utils.JsonUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Wanj on 2017/1/13.
 */
@RequestMapping("ums")
@Controller
public class UmsText {

    @Resource
    ErpBaseMenuService erpBaseMenuService;

    @Resource
    UserContract userContract;

    @RequestMapping("home")
    public String home(){
        return "umsText/ums";
    }

/*    @RequestMapping("selectAll")
    public String selectAll(ModelMap map){
        System.out.print("222Text");
        List<ErpUserContract> list = userContract.selectAll();
        map.put("list",list);
        return "umsText/ums1";
    }*/

    @RequestMapping("selectUserTen")
    public String selectUserTen(ModelMap map){
        System.out.print("222Text");
        List<ErpUser> list = userContract.selectUserTen();
        map.put("list",list);
        return "umsText/ums1";
    }

    @RequestMapping("selectUserBy")
    public String selectUserBy(ModelMap map){
        String username ="xa31202";
        String userpwd = "E10ADC3949BA59ABBE56E057F20F883E";
        ErpUser i = userContract.selectUserBy(username,userpwd);
        if(i!=null){
            map.put("success","成功"+i);
            return "common/success";
        }
        map.put("error","失败"+i);
        return "common/error";
    }

    @RequestMapping("selectByUser")
    public String selectByUser(ModelMap map,ErpUser ErpUser){
        String username ="xa31202";
        String userpwd = "E10ADC3949BA59ABBE56E057F20F883E";
        ErpUser.setUserName(username);
        ErpUser.setUserPwd(userpwd);
        int i = userContract.selectByUser(ErpUser);
        if(i==1){
            map.put("success","成功"+i);
            return "common/success";
        }
        map.put("error","失败"+i);
        return "common/error";
    }

    @RequestMapping("selectByPrimaryKey")
    public String selectByPrimaryKey(ModelMap map,ErpUser ErpUser){
        String username ="xa31202";
        String userpwd = "E10ADC3949BA59ABBE56E057F20F883E";
        ErpUser.setUserName(username);
        ErpUser.setUserPwd(userpwd);
        String userid="1051612010948041791";
        ErpUser i = userContract.selectByPrimaryKey(userid);
        if(i!=null){
            map.put("success","成功"+i);
            return "common/success";
        }
        map.put("error","失败"+i);
        return "common/error";
    }

    /**
     * 根据用户名查找用户
     * @param map
     * @param ErpUser
     * @return
     */
    @RequestMapping("selectUserOne")
    public String selectUserOne(ModelMap map,ErpUser ErpUser){
        String username ="xa31202";
        String userpwd = "E10ADC3949BA59ABBE56E057F20F883E";
        ErpUser.setUserName(username);
        ErpUser.setUserPwd(userpwd);
        String userid="1051612010948041791";
        ErpUser i = userContract.selectUserOne(username);
        if(i!=null){
            map.put("success","成功"+i.getUserName()+i.getUserPwd());
            return "common/success";
        }
        map.put("error","失败"+i);
        return "common/error";
    }

    /**
     * 用户名+密码查**
     * @param map
     * @param ErpUser
     * @return
     */
    @RequestMapping("selectUserPwd")
    public String selectUserPwd(ModelMap map,ErpUser ErpUser){
        String username ="xa31202";
        String userpwd = "E10ADC3949BA59ABBE56E057F20F883E";
        ErpUser.setUserName(username);
        ErpUser.setUserPwd(userpwd);
        String userid="1051612010948041791";
        String userPwd = userContract.selectUserPwd(username,userpwd);
        if(userPwd!=null){
            map.put("success","成功"+userPwd);
            return "common/success";
        }
        map.put("error","失败"+userPwd);
        return "common/error";
    }

    /**
     * 用户名+密码查**
     * @param map
     * @param ErpUser
     * @return
     */
    @RequestMapping("selectByNamePass")
    public String selectByNamePass(ModelMap map,ErpUser ErpUser){
        String username ="xa31202";
        String userpwd = "E10ADC3949BA59ABBE56E057F20F883E";
        ErpUser.setUserName(username);
        ErpUser.setUserPwd(userpwd);
        String userid="1051612010948041791";
        boolean stat = userContract.selectByNamePass(ErpUser);
        if(stat){
            map.put("success","成功"+stat);
            return "common/success";
        }
        map.put("error","失败"+stat);
        return "common/error";
    }


    @RequestMapping("list")
    public String list(ModelMap map){
        List<ErpBaseMenuCtPo> erpBaseMenuCtsList=erpBaseMenuService.selectErpBaseMenuList("1051612080943171157");
        String userDataJson= JsonUtils.toJson(erpBaseMenuCtsList);
        map.put("userData",userDataJson);
        return "umsText/home";
    }

    /**
     * 根据用户名和密码返回用户信息与所拥有资源菜单
     * @param map
     * @return
     */
    @RequestMapping(value = "selectErpUserBase",method = RequestMethod.GET)
    public String selectErpUserBase(ModelMap map){
        String username = "xa123";
        String password = "E10ADC3949BA59ABBE56E057F20F883E";
        Map<String,String> orMap = new HashedMap();
        orMap.put("username",username);
        orMap.put("password",password);
        Response response = userContract.selectErpUserBase(orMap);
        Map mapw = (Map)response.getBody();
        Object object=(Object)mapw.get("user");
        System.out.print("########################");
        Object erpBaseMenuCtPo= (Object)mapw.get("base");
        map.put("userData",erpBaseMenuCtPo);
        map.put("ErpUser",object);
        return "umsText/home";
    }


}
