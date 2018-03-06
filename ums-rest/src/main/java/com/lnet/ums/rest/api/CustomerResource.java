package com.lnet.ums.rest.api;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerEntity.Project;
import com.lnet.ums.rest.api.Impl.CustomerApplication;
import com.lnet.ums.rest.api.Impl.ProjectApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
@RestController
@RequestMapping("customer")
public class CustomerResource {

    @Resource
    CustomerApplication customerApplication;

    @Resource
    ProjectApplication projectApplication;

    @GetMapping(value = "getAvailable")
    public Response<List<Customer>> getAvailable() {
        return customerApplication.getAvailable();
    }

    @RequestMapping(value = "getProject/{branchCode}/{customerCode}")
    public Response<Project> getProject(@PathVariable("branchCode") String branchCode, @PathVariable("customerCode") String customerCode) {
        return projectApplication.getProject(branchCode,customerCode);
    }
}
