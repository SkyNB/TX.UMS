package com.lnet.ums.rest.api;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.organization.Organization;
import com.lnet.model.ums.site.Site;
import com.lnet.ums.rest.api.Impl.OrganizationApplication;
import com.lnet.ums.rest.api.Impl.SiteApplication;
import com.lnet.ums.rest.api.Impl.UserApplication;
import com.lnet.ums.rest.api.uitls.SystemUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/29.
 */
@RestController
@RequestMapping("user")
public class UserResource {
    @Resource
    UserApplication userApplication;

    @Resource
    OrganizationApplication organizationApplication;


    @Resource
    SiteApplication siteApplication;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Object login(@RequestBody Map<String, String> params) {
        return userApplication.login(params.get("userName"), params.get("password"));
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Object updatePassword(@RequestBody Map<String, String> params) {
        return userApplication.modifyPassword(params.get("userName"), params.get("oldPassword"), params.get("newPassword"));
    }

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
        Response<List<Site>> branchCode1= siteApplication.getByBranchCode(branchCode);
        branchCode1.setMessage("text");
        return  branchCode1;
        //return siteApplication.getByBranchCode(branchCode);
    }

    @GetMapping("getAllSites/{userId}")
    public Response<List<Site>> getAllSites(@PathVariable String userId) {
        return userApplication.getAllSites(userId);
    }


}
