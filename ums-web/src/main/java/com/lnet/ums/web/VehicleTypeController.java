package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.ums.contract.api.VehicleTypeService;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicleType")
public class VehicleTypeController {
    @Autowired
    private VehicleTypeService vehicleTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "vehicleType/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit() {
        return "vehicleType/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody VehicleTypeDto vehicleTypeDto) {
        return vehicleTypeService.update(vehicleTypeDto);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "vehicleType/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody VehicleTypeDto vehicleTypeDto) {
        return vehicleTypeService.create(vehicleTypeDto);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<VehicleTypeDto>> search(@RequestBody KendoGridRequest params) {
        return vehicleTypeService.search(params.getParams());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response get(@PathVariable String id) {
        return vehicleTypeService.get(id);
    }
}
