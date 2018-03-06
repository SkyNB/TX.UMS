package com.lnet.ums.rest.api.Impl;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.site.Site;
import com.lnet.ums.contract.api.SiteService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SiteApplication {
    @Resource
    private SiteService siteService;

    public Object pageList(KendoGridRequest request) {
        return siteService.pageList(request.getPage(), request.getPageSize(), request.getParams());
    }

    public Response get(String siteId) {
        return siteService.get(siteId);
    }

    public Response<List<Site>> getByBranchCode(String branchCode) {
        return siteService.getByBranchCode(branchCode);
    }

    public Response delete(String siteId) {
        return siteService.delete(siteId);
    }

    public Response create(Site site) {
        return siteService.create(site);
    }

    public Response update(Site site) {
        return siteService.update(site);
    }

    public Response<Site> getByCode(String code) {
        return siteService.getByCode(code);
    }

}
