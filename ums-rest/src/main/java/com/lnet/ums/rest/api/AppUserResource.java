package com.lnet.ums.rest.api;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.ErpUser;
import com.lnet.ums.rest.api.uitls.MD5Util;
import com.lnet.ums.contract.api.ErpBaseMenuService;
import com.lnet.ums.contract.api.UserContract;
import com.lnet.model.ums.po.ErpBaseMenuCtPo;
import com.lnet.model.ums.user.User;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/29.
 */
@RestController
@RequestMapping("xinUser")
public class AppUserResource {

    @Resource
    UserContract userContract;

    @Resource
    ErpBaseMenuService erpBaseMenuService;


    /*@Resource
    UserContract userLogin;*/
/*    @Resource
    UserApplication userApplication;

    @Resource
    OrganizationApplication organizationApplication;


    @Resource
    SiteApplication siteApplication;*/

    @GetMapping(value = "loginw", produces = "application/json")
    public Object loginw() {
        Map<String, Object> testw = new HashedMap();
        User user = new User();
        user.setUsername("xa222");
        user.setPassword("123456");
        testw.put("time", user.getUsername());
        testw.put("date", user.getPassword());
        return testw;
    }

    @GetMapping(value = "loginText", produces = "application/json")
//    @ResponseBody
    public Object loginText() {
        Map<String, Object> testw = new HashedMap();
        User user = new User();
        user.setUsername("xa222");
        user.setPassword("123456");
        String s = user.getUsername();
        testw.put("userNmae", user.getUsername());
        testw.put("password", user.getPassword());
        return testw;
    }

    @GetMapping(value = "login", produces = "application/json")
    public Object login() {
        Map<String, Object> test = new HashedMap();
        test.put("time", System.currentTimeMillis());
        test.put("date", LocalDate.now());
        test.put("time", LocalDateTime.now());
        return test;
    }

    @RequestMapping(value = "logiqq", method = RequestMethod.POST)
    public User logiqqn(String name) {
        User user = new User();
        user.setUsername("xa222");
        user.setEmail("234234@qq.com");
        return user;
        /*return userLogin.login(params.get("userName"), params.get("password"));*/
    }

    /**
     * 根据用户名和密码登录验证
     * @param params
     * @return
     */
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    public @ResponseBody Response userLogin(@RequestBody Map<String,String> params) {
        Map<String,String> maps = new HashMap<String,String>();
        maps.put("username",params.get("username"));
        maps.put("userpwd", MD5Util.MD5(params.get("userpwd")));
        ErpUser erpuser = userContract.selectErpUser(maps);
        Response response = new Response();
        if(erpuser!=null&&erpuser.getUserId()!=null){
            List<ErpBaseMenuCtPo> erpBaseMenuCtsList=erpBaseMenuService.selectErpBaseMenuList(erpuser.getUserId());
            if(erpBaseMenuCtsList!=null){
                Map<String,Object> rquestMap = new HashMap<String,Object>();
                rquestMap.put("user",erpuser);
                rquestMap.put("base",erpBaseMenuCtsList);
                response.setBody(rquestMap);
                response.setSuccess(true);
                response.setMessage("成功");
            }else{
                response.setSuccess(false);
                response.setMessage("失败");
            }
        }
        return response;
    }

    /**
     * 根据用户名和密码登录验证
     * @param params
     * @return
     */
    @RequestMapping(value = "selectErpUserBase", method = RequestMethod.POST)
    public @ResponseBody Response selectErpUserBase(@RequestBody Map<String,String> params) {
//        Map<String,String> orMap = new HashedMap();
//        orMap.put("username",params.get("userName"));
//        orMap.put("userpwd",params.get("passWord"));
        System.out.print("#####################通过#############################");
        Response response = userContract.selectErpUserBase(params);
        return response;
    }

    @RequestMapping(value = "logins", method = RequestMethod.POST)
    public User login(@RequestBody Map<String, String> params) {
        User user = new User();
        user.setUsername("xa31202");
        user.setEmail("234234@qq.com");
        user.setPassword("123456");
        return user;
        /*return userLogin.login(params.get("userName"), params.get("password"));*/
    }

/*    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Object updatePassword(@RequestBody Map<String, String> params) {
        return userApplication.modifyPassword(params.get("userName"), params.get("oldPassword"), params.get("newPassword"));
    }*/
/*
    @GetMapping("findBySys/{sysCode}")
    public Object getUser(@PathVariable String sysCode) {
        return userApplication.getAvailable(sysCode);
    }

    @GetMapping("getBindings/{userId}")
    public Object getBindings(@PathVariable String userId) {
        return userApplication.getBindings(userId);
    }


    @GetMapping("getAllBranches")
    public Response<List<Organization>> getAllBranches() {
        return organizationApplication.getAllBranches(SystemUtil.SYSTEM_CODE);
    }


    @GetMapping("getByBranchCode/{branchCode}")
    public Response<List<Site>> getByBranchCode(@PathVariable String branchCode) {
        return siteApplication.getByBranchCode(branchCode);
    }

    @GetMapping("getAllSites/{userId}")
    public Response<List<Site>> getAllSites(@PathVariable String userId) {
        return userApplication.getAllSites(userId);
    }*/


}
