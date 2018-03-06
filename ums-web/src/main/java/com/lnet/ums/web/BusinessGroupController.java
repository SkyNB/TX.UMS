package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.ums.contract.api.BusinessGroupService;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.model.ums.customer.customerEntity.BusinessGroup;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/businessGroup")
public class BusinessGroupController {
    @Resource
    private BusinessGroupService businessGroupService;
    @Resource
    private CustomerService customerService;
    @Autowired
    private UserPrincipalImpl userPrincipal;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "businessGroup/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody KendoGridRequest params) {
        return businessGroupService.pageList(params.getPage(),params.getPageSize(),params.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "businessGroup/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody BusinessGroup businessGroup) {
        businessGroup.setCreateUserId(userPrincipal.getUserId());
        businessGroup.setCreateUserName(userPrincipal.getDisplayName());
        return businessGroupService.create(businessGroup);
    }

    @RequestMapping(value = "/edit/{code}", method = RequestMethod.GET)
    public String edit(@PathVariable String code, ModelMap map) {
        map.addAttribute("cusForBiz", customerService.getByBizGroupCode(code).getBody());
        return "businessGroup/update";
    }

    /**
     * function: 业务组的code查询
     * @param code
     * @return
     */
    @RequestMapping(value = "getOrganization/{code}", method = RequestMethod.GET)
    public String getOrganization(@PathVariable String code,ModelMap map) {
        map.put("businessGroup", businessGroupService.getByCode(code));
        return "businessGroup/create";

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody BusinessGroup businessGroup) {
        businessGroup.setModifyUserId(userPrincipal.getUserId());
        businessGroup.setModifyUserName(userPrincipal.getDisplayName());
        return businessGroupService.update(businessGroup);
    }

    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response<BusinessGroup> getByCode(@PathVariable String code, HttpSession session) {
        return businessGroupService.getByCode(code);
    }
}
