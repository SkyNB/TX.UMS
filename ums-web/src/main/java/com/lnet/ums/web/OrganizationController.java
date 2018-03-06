package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.core.SelectObject;
import com.lnet.ums.contract.api.OrganizationService;
import com.lnet.model.ums.organization.Organization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/7/12.
 */
@Controller
@RequestMapping(value = "organization")
public class OrganizationController {

    @Resource
    OrganizationService organizationService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "organization/index";
    }

    @RequestMapping(value = "getHierarchical", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Organization> getHierarchical() {
        return organizationService.getHierarchical("LNET").getBody();
    }

    @RequestMapping(value = "get/{organizationId}", method = RequestMethod.POST)
    public
    @ResponseBody
    Object get(@PathVariable String organizationId) {
        return organizationService.get(organizationId).getBody();
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public
    @ResponseBody
    Object search(@RequestBody KendoGridRequest params) {
        return organizationService.pageList(params.getPage(), params.getPageSize(), params.getParams());
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public
    @ResponseBody
    Response create(@RequestBody Organization organization) {
        //// TODO: 2016/7/16 改成直接从配置文件中读取
        organization.setSystemCode("TMS");
        organization.setActive(true);
        return organizationService.create(organization);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Response update(@RequestBody Organization organization) {
        return organizationService.update(organization);
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        map.put("types", Organization.type.values());
        return "organization/create";
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(ModelMap map) {
        map.put("types", Organization.type.values());
        return "organization/update";
    }

    @RequestMapping(value = "delete/{organizationId}", method = RequestMethod.GET)
    public
    @ResponseBody
    Response deleteOrganizationId(@PathVariable String organizationId) {
        return organizationService.delete(organizationId);
    }

    /**
     * function:组织结构的删除
     * @param organizationIds
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Response delete(@RequestBody List<String> organizationIds) {
        try {
            Response response = ResponseBuilder.success("删除成功");
            for (String organizationId : organizationIds) {
                response = organizationService.delete(organizationId);
            }
            return response;
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "activate", method = RequestMethod.POST)
    public
    @ResponseBody
    Response activate(@RequestBody String[] codes) {
        try {
            return organizationService.activate(codes);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "inactivate", method = RequestMethod.POST)
    public
    @ResponseBody
    Response inactivate(@RequestBody String[] codes) {
        try {
            return organizationService.inactivate(codes);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/getBranchesForSelect", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectObject> getBranchesForSelect() {
        //// TODO: 2016/8/1 根据登录信息获取相应分公司
        Response<List<Organization>> resp = organizationService.getAllBranches("TMS");
        if (resp.isSuccess()) {
            return resp.getBody().stream()
                    .map(m -> new SelectObject(m.getOrganizationId(), m.getCode(), m.getName()))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * function: 组织架构的organizationId查询
     * @param organizationId
     * @return
     */
    @RequestMapping(value = "getOrganization/{organizationId}", method = RequestMethod.GET)
    public String getOrganization(@PathVariable String organizationId,ModelMap map) {
        map.put("organization", organizationService.get(organizationId));
        return "organization/create";

    }
}
