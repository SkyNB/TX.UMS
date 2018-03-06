package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.SelectObject;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.ums.contract.api.OrganizationService;
import com.lnet.ums.contract.api.ProjectService;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerEntity.Project;
import com.lnet.model.ums.organization.Organization;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;
    @Resource
    private CustomerService customerService;
    @Resource
    private OrganizationService organizationService;
    @Resource
    private UserPrincipalImpl userPrincipal;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        this.setMap(map);
        map.remove("settleCycle");
        map.remove("calculateType");
        map.remove("paymentType");
        map.remove("handoverType");
        map.remove("receivableDataSource");
        return "project/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody KendoGridRequest params) {
        params.setParams("branchCode", userPrincipal.getCurrentBranchCode());
        return projectService.pageList(params.getPage(),params.getPageSize(),params.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        this.setMap(map);
        return "project/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody Project project) {
        project.setBranchCode(userPrincipal.getCurrentBranchCode());
        project.setCreateUserId(userPrincipal.getUserId());
        project.setCreateUserName(userPrincipal.getDisplayName());
        return projectService.create(project);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(ModelMap map) {
        this.setMap(map);
        return "project/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody Project project) {
        return projectService.update(project);
    }

    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response getByCode(@PathVariable String code) {
        return projectService.getByCode(code);
    }

    @RequestMapping(value = "/forbidden/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response forbidden(@PathVariable String code) {
        return projectService.inactivate(code);
    }

    @RequestMapping(value = "/invocation/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response invocation(@PathVariable String code) {
        return projectService.activate(code);
    }

    //ModelMap属性设置
    private void setMap(ModelMap map) {
        Response<List<Customer>> customers = customerService.getAvailable();
        Response<List<Organization>> branches = organizationService.getAllBranches("TMS");
        List<SelectObject> cusObject = new ArrayList<>();
        List<SelectObject> branchObject = new ArrayList<>();

        if (customers.isSuccess())
            cusObject = customers.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());
        if (branches.isSuccess())
            branchObject = branches.getBody().stream().map(m -> new SelectObject(m.getOrganizationId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customers", cusObject);
        map.addAttribute("branches", branchObject);
        map.addAttribute("settleCycle", Project.SettleCycleEnum.values());
        map.addAttribute("calculateType", Project.CalculateTypeEnum.values());
        map.addAttribute("paymentType", Project.PaymentTypeEnum.values());
        map.addAttribute("handoverType", Project.HandoverTypeEnum.values());
        map.addAttribute("receivableDataSource", Project.ReceivableDataSourceEnum.values());
    }

    @RequestMapping(value = "/getProject/{customerCode}", method = RequestMethod.GET)
    @ResponseBody
    public Project getProject(@PathVariable String customerCode) {
        Response<Project> resp = projectService.getProject(userPrincipal.getCurrentBranchCode(), customerCode);
        return resp.getBody();
    }
}
