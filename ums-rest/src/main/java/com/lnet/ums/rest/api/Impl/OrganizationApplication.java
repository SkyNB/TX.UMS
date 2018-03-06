package com.lnet.ums.rest.api.Impl;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.model.ums.organization.Organization;
import com.lnet.ums.contract.api.OrganizationService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
@Component
public class OrganizationApplication {

    @Resource
    OrganizationService organizationService;

    public Response<List<Organization>> getHierarchical(String code) {
        return organizationService.getHierarchical(code);
    }

    public Response<Organization> get(String organizationId) {
        return organizationService.get(organizationId);
    }

    public Object pageList(KendoGridRequest params) {
        return organizationService.pageList(params.getPage(), params.getPageSize(), params.getParams());
    }

    public Response create(Organization organization) {
        return organizationService.create(organization);
    }

    public Response update(Organization organization) {
        return organizationService.update(organization);
    }


    public Response delete(String organizationId) {
        return organizationService.delete(organizationId);
    }

    public Response activate(String[] codes) {
        try {
            return organizationService.activate(codes);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    public Response inactivate(String[] codes) {
        try {
            return organizationService.inactivate(codes);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    public Response<List<Organization>> getAllBranches(String sysCode) {
        return organizationService.getAllBranches(sysCode);
    }
}
