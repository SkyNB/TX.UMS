package com.lnet.ums.rest.api;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.site.Site;
import com.lnet.ums.rest.api.Impl.SiteApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/11/24.
 */
@RestController
@RequestMapping("site")
public class SiteResource {
    @Resource
    SiteApplication siteApplication;

    @RequestMapping("getByCode/{code}")
    public Response<Site> getByCode(@PathVariable String code) {
        return siteApplication.getByCode(code);
    }
}
