package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.SelectObject;
import com.lnet.model.ums.customer.customerEntity.DeliveryAddress;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.ums.contract.api.DeliveryAddressService;
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
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController {
    @Resource
    private DeliveryAddressService deliveryAddressService;
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
        return "deliveryAddress/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody KendoGridRequest params) {
        return deliveryAddressService.pageList(params.getPage(), params.getPageSize(), params.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        Response<List<Customer>> customers = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<Object> cusObject = new ArrayList<>();

        if (customers.isSuccess())
            cusObject = customers.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customers", cusObject);
        return "deliveryAddress/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody DeliveryAddress deliveryAddress) {
        deliveryAddress.setActive(true);
        return deliveryAddressService.create(deliveryAddress);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(ModelMap map) {
        Response<List<Customer>> customers = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<Object> cusObject = new ArrayList<>();

        if (customers.isSuccess())
            cusObject = customers.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customers", cusObject);
        return "deliveryAddress/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody DeliveryAddress deliveryAddress) {
        return deliveryAddressService.update(deliveryAddress);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<DeliveryAddress> get(@PathVariable String id) {
        return deliveryAddressService.get(id);
    }

    @RequestMapping(value = "/activate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response activate(@PathVariable String id) {
        return deliveryAddressService.activate(id);
    }

    @RequestMapping(value = "/inactivate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response inactivate(@PathVariable String id) {
        return deliveryAddressService.inactivate(id);
    }

    @RequestMapping(value = "getByOwnerCode/{ownerCode}")
    @ResponseBody
    public Object getByOwnerCode(@PathVariable String ownerCode) {
        return deliveryAddressService.findByOwnerCode(ownerCode).getBody();
    }
}
