package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.core.SelectObject;
import com.lnet.ums.contract.api.OrganizationService;
import com.lnet.ums.contract.api.VehicleService;
import com.lnet.ums.contract.api.VehicleTypeService;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleCreateDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleListDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleTypeDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleUpdateDto;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    UserPrincipalImpl userPrincipal;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleTypeService vehicleTypeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.addAttribute("leaseType", VehicleListDto.LeaseType.values());
        map.addAttribute("status", VehicleListDto.VehicleStatus.values());
        return "vehicle/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        List<SelectObject> objects = new ArrayList<>();
        List<SelectObject> branches = new ArrayList<>();

        Response<List<VehicleTypeDto>> response = vehicleTypeService.findAll();
        if (response.isSuccess())
            objects = response.getBody().stream().map(ele -> new SelectObject(ele.getVehicleTypeId(), ele.getName())).collect(Collectors.toList());

        if (organizationService.getAllBranches("TMS").isSuccess()) {
            branches = organizationService.getAllBranches("TMS").getBody().stream().map(ele -> new SelectObject(ele.getCode(), ele.getName())).collect(Collectors.toList());
        }
        map.addAttribute("leaseType", VehicleListDto.LeaseType.values());
        map.addAttribute("paymentTypes", VehicleListDto.PaymentType.values());
        map.addAttribute("vehicleTypes", objects);
        //map.addAttribute("branches", branches);
        return "vehicle/create";
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody VehicleCreateDto vehicleCreateDto) {
        vehicleCreateDto.setIsActive(true);
        return vehicleService.create(vehicleCreateDto);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(ModelMap map) {
        List<SelectObject> objects = new ArrayList<>();

        Response<List<VehicleTypeDto>> response = vehicleTypeService.findAll();
        if (response.isSuccess())
            objects = response.getBody().stream().map(ele -> new SelectObject(ele.getVehicleTypeId(), ele.getName())).collect(Collectors.toList());

        map.addAttribute("leaseType", VehicleListDto.LeaseType.values());
        map.addAttribute("paymentTypes", VehicleListDto.PaymentType.values());
        map.addAttribute("vehicleTypes", objects);
        return "vehicle/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody VehicleUpdateDto vehicleUpdateDto) {
        return vehicleService.update(vehicleUpdateDto);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<VehicleListDto>> search(@RequestBody KendoGridRequest params) {
        params.setParams("branchCode", userPrincipal.getCurrentBranchCode());
        params.setParams("siteCode", userPrincipal.getCurrentSiteCode());
        return vehicleService.search(params.getParams());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<VehicleListDto> get(@PathVariable String id) {
        return vehicleService.get(id);
    }

    @RequestMapping(value = "/inactivate/{vehicleNo}", method = RequestMethod.POST)
    @ResponseBody
    public Response inactivate(@PathVariable String vehicleNo) {
        return vehicleService.inactivate(vehicleNo);
    }

    @RequestMapping(value = "/activate/{vehicleNo}", method = RequestMethod.POST)
    @ResponseBody
    public Response activate(@PathVariable String vehicleNo) {
        return vehicleService.activate(vehicleNo);
    }

    @RequestMapping(value = "/getAvailableForSelect", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectObject> getAvailableForSelect() {
        Response<List<VehicleListDto>> resp = vehicleService.getSiteIdle(userPrincipal.getCurrentBranchCode(),
                userPrincipal.getCurrentSiteCode());
        if (resp.isSuccess()) {
            return resp.getBody().stream()
                    .map(m -> new SelectObject(m.getVehicleId(), m.getVehicleId(), m.getVehicleNo() + " " + m.getDriver()))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/getBranchAvailable", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<VehicleListDto>> getBranchAvailable() {
        return vehicleService.getBranchAvailable(userPrincipal.getCurrentBranchCode());
    }

    @RequestMapping(value = "/downLoad", method = RequestMethod.GET)
    public void downLoad(@RequestParam("fileName") String fileName, HttpServletResponse response) throws FileNotFoundException, IOException {
        InputStream input = new FileInputStream(servletContext.getRealPath("/downloads/" + fileName));
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream();

        byte[] bytes = new byte[1024];
        int length = 0;

        while ((length = input.read(bytes)) > 0) {
            out.write(bytes, 0, length);
        }
        input.close();
        out.close();
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importVehicle() {
        return "vehicle/import";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Object importVehicle(@RequestParam("vehicleFile") MultipartFile file) {
        try {
            return vehicleService.importVehicle(file.getInputStream(), userPrincipal.getCurrentBranchCode(), userPrincipal.getCurrentSiteCode());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseBuilder.fail(e);
        }
    }

    @RequestMapping(value = "/getSiteAvailable", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<VehicleListDto>> getSiteAvailable() {
        return vehicleService.getSiteAvailable(userPrincipal.getCurrentBranchCode(), userPrincipal.getCurrentSiteCode());
    }
}
