package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.SelectObject;
import com.lnet.ums.contract.api.CarrierService;
import com.lnet.ums.contract.api.ShipAddressService;
import com.lnet.model.ums.carrier.carrierDto.CarrierListDto;
import com.lnet.model.ums.customer.customerEntity.ShipAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shipAddress")
public class ShipAddressController {
    @Autowired
    private CarrierService carrierService;
    @Autowired
    private ShipAddressService shipAddressService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        List<SelectObject> carriers = new ArrayList<>();
        Response<List<CarrierListDto>> carrierResponse = carrierService.getBranchAvailable("SZ");

        if (carrierResponse.isSuccess())
            carriers = carrierResponse.getBody().stream().map(ele -> new SelectObject(ele.getCarrierId(), ele.getCode(), ele.getName())).collect(Collectors.toList());

        map.addAttribute("carriers", carriers);
        return "shipAddress/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse search(@RequestBody KendoGridRequest request) {
        return shipAddressService.pageList(request.getPage(), request.getPageSize(), request.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        List<SelectObject> carriers = new ArrayList<>();
        Response<List<CarrierListDto>> carrierResponse = carrierService.getBranchAvailable("SZ");

        if (carrierResponse.isSuccess())
            carriers = carrierResponse.getBody().stream().map(ele -> new SelectObject(ele.getCarrierId(), ele.getCode(), ele.getName())).collect(Collectors.toList());

        map.addAttribute("carriers", carriers);
        return "shipAddress/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> create(@RequestBody ShipAddress shipAddress) {
        shipAddress.setIsActive(true);
        return shipAddressService.create(shipAddress);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String update(ModelMap map) {
        List<SelectObject> carriers = new ArrayList<>();
        Response<List<CarrierListDto>> carrierResponse = carrierService.getBranchAvailable("SZ");

        if (carrierResponse.isSuccess())
            carriers = carrierResponse.getBody().stream().map(ele -> new SelectObject(ele.getCarrierId(), ele.getCode(), ele.getName())).collect(Collectors.toList());

        map.addAttribute("carriers", carriers);
        return "shipAddress/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> update(@RequestBody ShipAddress shipAddress) {
        return shipAddressService.update(shipAddress);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<ShipAddress> get(@PathVariable String id) {
        return shipAddressService.get(id);
    }

    @RequestMapping(value = "/inactivate/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response inactivate(@PathVariable String code) {
        return shipAddressService.inactivate(code);
    }

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response activate(@PathVariable String code) {
        return shipAddressService.activate(code);
    }
}
