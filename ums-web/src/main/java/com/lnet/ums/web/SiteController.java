package com.lnet.ums.web;

import com.lnet.framework.core.*;
import com.lnet.ums.contract.api.SiteService;
import com.lnet.model.ums.site.Site;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/site")
public class SiteController {
    @Resource
    private SiteService siteService;
    @Autowired
    private UserPrincipalImpl userPrincipal;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        return "site/index";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public
    @ResponseBody
    PageResponse<Site> search(@RequestBody KendoGridRequest request) {
        request.setParams("branchCode", userPrincipal.getCurrentBranchCode());
        request.setParams("siteCode", userPrincipal.getCurrentSiteCode());
        return siteService.pageList(request.getPage(), request.getPageSize(), request.getParams());
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        return "site/create";
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(ModelMap map) {
        return "site/update";
    }

    @RequestMapping(value = "get/{siteId}", method = RequestMethod.GET)
    public String get(@PathVariable String siteId,ModelMap map) {
        map.put("site", siteService.get(siteId));
        return "site/create";

    }

    @RequestMapping(value = "/getAvailableForSelect/{branchCode}", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectObject> getAvailableForSelect(@PathVariable String branchCode) {
        Response<List<Site>> resp = siteService.getByBranchCode(branchCode);
        if (resp.isSuccess()) {
            return resp.getBody().stream()
                    .map(m -> new SelectObject(m.getSiteId(), m.getCode(), m.getName()))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/getByBranchCode/{branchCode}", method = RequestMethod.GET)
    @ResponseBody
    public List<Site> getByBranchCode(@PathVariable String branchCode) {
        Response<List<Site>> resp = siteService.getByBranchCode(branchCode);
        if (resp.isSuccess()) {
            return resp.getBody();
        } else {
            return null;
        }
    }

    /**
     * function:站点的删除
     * @param siteIds
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Response delete(@RequestBody List<String> siteIds) {
        try {
            Response response = ResponseBuilder.success("删除成功");
            for (String siteId : siteIds) {
                response = siteService.delete(siteId);
            }
            return response;
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }


    /**
     * 站点创建
     * @param site
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public
    @ResponseBody
    Response create(@RequestBody Site site) {
        try {
            //site.setBranchCode(userPrincipal.getBindings().get("ORGANIZATION"));
            //todo 此次为硬编码，需要修改
            site.setBranchCode("SZ");
            return siteService.create(site);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Response update(@RequestBody Site site) {
        try {
            return siteService.update(site);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

}
