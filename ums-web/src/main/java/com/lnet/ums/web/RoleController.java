package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.ums.contract.api.RoleService;
import com.lnet.ums.contract.model.Permission;
import com.lnet.model.ums.role.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Resource
    RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "role/index";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public
    @ResponseBody
    Object search(@RequestBody KendoGridRequest request) {
        return roleService.pageList(request.getPage(),request.getPageSize(),request.getParams());
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Permission p : Permission.values()) {
            Map<String, String> mp = new HashMap<>();
            mp.put("code", p.getCode());
            mp.put("name", p.getName());
            list.add(mp);
        }
        map.put("permissions", list);
        return "role/create";
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(ModelMap map) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Permission p : Permission.values()) {
            Map<String, String> mp = new HashMap<>();
            mp.put("code", p.getCode());
            mp.put("name", p.getName());
            list.add(mp);
        }
        map.put("permissions", list);
        return "role/update";
    }

    @RequestMapping(value = "get/{roleId}", method = RequestMethod.GET)
    public String get(@PathVariable String roleId,ModelMap map) {
        map.put("role", roleService.get(roleId));
        return "role/create";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Response delete(@RequestBody List<String> roleIds) {
        try {
            Response response = ResponseBuilder.success("删除成功");
            for (String roleId : roleIds) {
                response = roleService.delete(roleId);
            }
            return response;
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public
    @ResponseBody
    Response create(@RequestBody Role role) {
        try {
            role.setSystemCode("LNET");
            role.setActive(true);
            return roleService.create(role);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Response update(@RequestBody Role role) {
        try {
            return roleService.update(role);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "activate", method = RequestMethod.POST)
    public
    @ResponseBody
    Response activate(@RequestBody String[] codes) {
        try {
            return roleService.activate(codes);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "inactivate", method = RequestMethod.POST)
    public
    @ResponseBody
    Response inactivate(@RequestBody String[] codes) {
        try {
            return roleService.inactivate(codes);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

}
