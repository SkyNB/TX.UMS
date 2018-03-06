package com.lnet.ums.rest.api.Impl;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.Project;
import com.lnet.ums.contract.api.ProjectService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProjectApplication {
    @Resource
    private ProjectService projectService;

    public Object pageList(KendoGridRequest params) {
        return projectService.pageList(params.getPage(), params.getPageSize(), params.getParams());
    }

    public Response create(Project project) {
        return projectService.create(project);
    }

    public Response edit(Project project) {
        return projectService.update(project);
    }

    public Response getByCode(String code) {
        return projectService.getByCode(code);
    }

    public Response forbidden(String code) {
        return projectService.inactivate(code);
    }

    public Response invocation(String code) {
        return projectService.activate(code);
    }

    public Response<Project> getProject(String branchCode, String customerCode) {
        return projectService.getProject(branchCode, customerCode);
    }
}
