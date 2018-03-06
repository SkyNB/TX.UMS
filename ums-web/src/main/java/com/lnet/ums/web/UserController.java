package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.security.UserPrincipal;
import com.lnet.ums.contract.api.RoleService;
import com.lnet.ums.contract.api.UserService;
import com.lnet.model.ums.role.Role;
import com.lnet.model.ums.user.User;
import com.lnet.model.ums.user.UserBinding;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Administrator on 2016/7/5.
 */
@Controller
@RequestMapping(value = "user")
public class UserController {
    @Resource
    private UserPrincipalImpl userPrincipal;
    @Resource
    UserService userService;
    @Resource
    RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.addAttribute("types", User.type.values());
        return "user/index";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        map.put("roles", roleService.getAvailable("TMS").getBody());
        return "user/create";
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(ModelMap map) {
        map.put("types", User.type.values());
        map.put("roles", roleService.getAvailable("TMS").getBody());
        return "user/update";
    }

    @RequestMapping(value = "/myInfo/{userId}", method = RequestMethod.GET)
    public String message(@PathVariable String userId, ModelMap map) {
        List<Role> myRoles = new ArrayList<>();
        User user = userService.get(userId).getBody();
        List<Role> roles = roleService.getAvailable("TMS").getBody();

        user.getRoleCodes().forEach(roleCode -> {
            Optional<Role> roleOption = roles.stream().filter(f -> f.getCode().equals(roleCode)).findFirst();
            if(roleOption.isPresent()) {
                myRoles.add(roleOption.get());
            }
        });

        map.addAttribute("userInfo",user);
        map.addAttribute("myRoles", myRoles);
        return "user/myInfo";
    }
    @RequestMapping(value = "/myPassword/{userId}", method = RequestMethod.GET)
    public String changePassword(@PathVariable String userId, ModelMap map) {
        User user = userService.get(userId).getBody();
        map.addAttribute("username", user.getUsername());
        return "user/myPassword";
    }

    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    @ResponseBody
    public Object modifyPassword(@RequestBody Map<String, Object> params) {
        String username = (String)params.get("username");
        String newPassword = (String)params.get("newPassword");
        String oldPassword = (String)params.get("oldPassword");
        return userService.modifyPassword(username,oldPassword, newPassword);
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String resetPassword(ModelMap map) {
        return "user/resetPassword";
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    Response resetPassword(@RequestBody Map<String, Object> map) {
        try {
            List<String> userIds = (ArrayList<String>) map.get("userIds");
            return userService.resetPassword(userIds, map.get("password").toString());
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    /**
     * function:用户的删除
     * @param userIds
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Response delete(@RequestBody List<String> userIds) {
        try {
            Response response = ResponseBuilder.success("删除成功");
            for (String userId : userIds) {
                response = userService.delete(userId);
            }
            return response;
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    /**
     * function:用户的创建
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public
    @ResponseBody
    Response create(@RequestBody User model) {
        try {
            model.setActive(true);
            model.setType(User.type.LNET);

            Response<User> CreatedUser = userService.create(model);
            if (CreatedUser.isSuccess()) {
                List<String> siteCodes = model.getSiteCodes();
                Response resp = userService.bind(CreatedUser.getBody().getUserId(), UserBinding.bindingType.SYSTEM, "TMS");
                Response resp2 = userService.bind(CreatedUser.getBody().getUserId(), UserBinding.bindingType.ORGANIZATION, model.getOrgCode());
                Response resp3 = userService.bind(CreatedUser.getBody().getUserId(), UserBinding.bindingType.SITE, siteCodes != null && 0 < siteCodes.size() ? siteCodes.toArray(new String[siteCodes.size()]) : null);
                return resp.isSuccess() && resp2.isSuccess() && resp3.isSuccess() ? ResponseBuilder.success() : ResponseBuilder.fail("创建失败！");
            }
            return CreatedUser;
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    /**
     * function: 用户的数据更新
     * @param model
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public
    @ResponseBody
    Response edit(@RequestBody User model) {
        try {

            Response<User> UpdatedUser = userService.update(model);
            if (UpdatedUser.isSuccess()) {
                List<String> siteCodes = model.getSiteCodes();
                Response resp = userService.bind(UpdatedUser.getBody().getUserId(), UserBinding.bindingType.ORGANIZATION, model.getOrgCode());

                Response resp3 = userService.bind(UpdatedUser.getBody().getUserId(), UserBinding.bindingType.SITE, siteCodes != null && 0 < siteCodes.size() ? siteCodes.toArray(new String[siteCodes.size()]) : null);
                return resp.isSuccess() && resp3.isSuccess() ? UpdatedUser : ResponseBuilder.fail("修改失败！");

            }
            return UpdatedUser;
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    /**
     * function：用户启用
     * @param userIds
     * @return
     */
    @RequestMapping(value = "enable", method = RequestMethod.POST)
    public
    @ResponseBody
    Response enable(@RequestBody List<String> userIds) {
        try {
            return userService.activate(userIds);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    /**
     * function：用户禁用
     * @param userIds
     * @return
     */
    @RequestMapping(value = "unEnable", method = RequestMethod.POST)
    public
    @ResponseBody
    Response unEnable(@RequestBody List<String> userIds) {
        try {
            return userService.inactivate(userIds);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    /**
     * function: 用户的userId查询
     * @param userId
     * @return
     */
    @RequestMapping(value = "get/{userId}", method = RequestMethod.GET)
    public String get(@PathVariable String userId,ModelMap map) {
        map.put("user", userService.get(userId));
        return "user/create";

    }

    /**
     * function：用户数据的查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody KendoGridRequest params) {
        params.setParams("siteCode", userPrincipal.getCurrentSiteCode());
        return userService.pageList(params.getPage(),params.getPageSize(),params.getParams());
    }

    @RequestMapping(value = "getAvailable/{sysCode}", method = RequestMethod.POST)
    public
    @ResponseBody
    Object getAvailable(@PathVariable String sysCode) {
        return userService.getAvailable(sysCode);
    }
}
