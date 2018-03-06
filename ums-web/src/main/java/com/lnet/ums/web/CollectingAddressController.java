package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.SelectObject;
import com.lnet.ums.contract.api.CollectingAddressService;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.model.ums.customer.customerEntity.CollectingAddress;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collectingAddress")
public class CollectingAddressController {
    @Resource
    private CollectingAddressService collectingAddressService;
    @Resource
    private CustomerService customerService;
    @Autowired
    private UserPrincipalImpl userPrincipal;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        Response<List<Customer>> customers = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<Object> cusObject = new ArrayList<>();

        if (customers.isSuccess())
            cusObject = customers.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customers", cusObject);
        map.addAttribute("types", CollectingAddress.typeEnum.values());
        return "collectingAddress/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody KendoGridRequest params) {
        return collectingAddressService.pageList(params.getPage(),params.getPageSize(),params.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        Response<List<Customer>> customers = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<Object> cusObject = new ArrayList<>();

        if (customers.isSuccess())
            cusObject = customers.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customers", cusObject);
        map.addAttribute("type", CollectingAddress.typeEnum.values());
        return "collectingAddress/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody CollectingAddress collectingAddress) {
        collectingAddress.setActive(true);
        return collectingAddressService.create(collectingAddress);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(ModelMap map) {
        Response<List<Customer>> customers = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<Object> cusObject = new ArrayList<>();

        if (customers.isSuccess())
            cusObject = customers.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customers", cusObject);
        map.addAttribute("type", CollectingAddress.typeEnum.values());
        return "collectingAddress/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody CollectingAddress collectingAddress) {
        return collectingAddressService.update(collectingAddress);
    }

    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response<CollectingAddress> get(@PathVariable String code) {
        return collectingAddressService.getByCode(code);
    }

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response activate(@PathVariable String code) {
        return collectingAddressService.activate(code);
    }

    @RequestMapping(value = "/inactivate/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response inactivate(@PathVariable String code) {
        return collectingAddressService.inactivate(code);
    }

    @RequestMapping(value = "/getByOwnerCode/{code}", method = RequestMethod.GET)
    @ResponseBody
    public List<CollectingAddress> getByOwnerCode(@PathVariable String code) {
        Response<List<CollectingAddress>> resp = collectingAddressService.getByOwnerCode(code);
        if (resp.isSuccess()) {
            return resp.getBody();
        } else {
            return null;
        }
    }
}
