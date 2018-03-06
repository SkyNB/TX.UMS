package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.SelectObject;
import com.lnet.framework.excel.reader.ExcelReader;
import com.lnet.framework.excel.util.ExcelFormat;
import com.lnet.framework.security.UserPrincipal;
import com.lnet.ums.contract.api.CarrierService;
import com.lnet.ums.contract.api.OrganizationService;
import com.lnet.ums.contract.api.VehicleService;
import com.lnet.model.ums.carrier.carrierDto.CarrierCreateDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierListDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierUpdateDto;
import com.lnet.model.ums.organization.Organization;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/carrier")
public class CarrierController {

    public static final String SYSTEM = "SYSTEM";
    @Autowired
    UserPrincipal userPrincipal;
    @Autowired
    private CarrierService carrierService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ServletContext context;

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.addAttribute("carrierTypes", CarrierDto.CarrierType.values());
        return "carrier/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<CarrierListDto>> search(@RequestBody KendoGridRequest request) {
        request.setParams("branchCode", userPrincipal.getCurrentBranchCode());
        return carrierService.search(request.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        List<SelectObject> branches = new ArrayList<>();

        Response<List<Organization>> organizationResponse = organizationService.getAllBranches(userPrincipal.getBinding(SYSTEM));
        if (organizationResponse.isSuccess()) {
            branches = organizationResponse.getBody().stream().map(ele -> new SelectObject(ele.getCode(), ele.getName())).collect(Collectors.toList());
        }

        map.addAttribute("branches", branches);
        map.addAttribute("carrierType", CarrierDto.CarrierType.values());
        map.addAttribute("settleCycle", CarrierDto.SettleCycle.values());
        map.addAttribute("paymentType", CarrierDto.PaymentType.values());
        map.addAttribute("calculateType", CarrierDto.CalculateType.values());
        map.addAttribute("transportType", CarrierDto.TransportType.values());
        return "carrier/create";
    }

    @RequestMapping(value = "getCarrierVehicle", method = RequestMethod.POST)
    public
    @ResponseBody
    Object getCarrierVehicle() {
        Response<List<VehicleListDto>> resp = vehicleService.getBranchAvailable(userPrincipal.getCurrentBranchCode());
        Response<List<CarrierListDto>> carrierResponse = carrierService.getBranchAvailable(userPrincipal.getCurrentBranchCode());
        if (resp.isSuccess() && carrierResponse.isSuccess()) {
            List<SelectObject> list = resp.getBody().stream()
                    .map(m -> new SelectObject(m.getVehicleId(), m.getVehicleNo(), m.getDriver()))
                    .collect(Collectors.toList());
            list.addAll(carrierResponse.getBody().stream()
                    .map(m -> new SelectObject(m.getCarrierId(), m.getCode(), m.getName()))
                    .collect(Collectors.toList()));
            return list;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> create(@RequestBody CarrierCreateDto carrierCreateDto) {
        carrierCreateDto.setIsActive(true);
        carrierCreateDto.setBranchCode(userPrincipal.getCurrentBranchCode());
        return carrierService.create(carrierCreateDto);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String update(ModelMap map) {
        List<SelectObject> branches = new ArrayList<>();

        Response<List<Organization>> organizationResponse = organizationService.getAllBranches(userPrincipal.getBinding(SYSTEM));
        if (organizationResponse.isSuccess()) {
            branches = organizationResponse.getBody().stream().map(ele -> new SelectObject(ele.getCode(), ele.getName())).collect(Collectors.toList());
        }

        map.addAttribute("branches", branches);
        map.addAttribute("carrierType", CarrierDto.CarrierType.values());
        map.addAttribute("settleCycle", CarrierDto.SettleCycle.values());
        map.addAttribute("paymentType", CarrierDto.PaymentType.values());
        map.addAttribute("calculateType", CarrierDto.CalculateType.values());
        map.addAttribute("transportType", CarrierDto.TransportType.values());
        return "carrier/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> edit(@RequestBody CarrierUpdateDto carrierUpdateDto) {
        return carrierService.update(carrierUpdateDto);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<CarrierListDto> get(@PathVariable String id) {
        return carrierService.get(id);
    }

    @RequestMapping(value = "/getBranchAvailable", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<CarrierListDto>> getBranchAvailable() {
        return carrierService.getBranchAvailable(userPrincipal.getCurrentBranchCode());
    }

    @RequestMapping(value = "/inactivate/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response inactivate(@PathVariable String code) {
        return carrierService.inactivate(code);
    }

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response activate(@PathVariable String code) {
        return carrierService.activate(code);
    }

    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response<CarrierListDto> getByCode(@PathVariable String code) {
        return carrierService.getByCode(code);
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importView() {
        return "carrier/import";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        InputStream input = new FileInputStream(context.getRealPath("/downloads/" + fileName));

        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream output = response.getOutputStream();

        byte[] bytes = new byte[1024];
        int length = 0;
        while ((length = input.read(bytes)) > 0) {
            output.write(bytes, 0, length);
        }

        input.close();
        output.close();
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Response importCarrier(@RequestParam("carrierFile") MultipartFile file) throws IOException {
        //读取excel数据
        List<CarrierCreateDto> createModels = ExcelReader.readByColumnName(file.getInputStream(), ExcelFormat.OFFICE2007, 0, 0, row -> {
            String code = (String) row.getColumnValue("编码");
            String name = (String) row.getColumnValue("名称");
            String type = (String) row.getColumnValue("类型");
            String settleCycle = (String) row.getColumnValue("结算周期");
            String paymentType = (String) row.getColumnValue("结算方式");
            String calculateType = (String) row.getColumnValue("计费方式");
            String transportType = (String) row.getColumnValue("运输方式");
            String contactMan = (String) row.getColumnValue("联系人");

            String address = (String) row.getColumnValue("详细地址");
            String remark = (String) row.getColumnValue("备注");
            String districtCode = null;
            String mobilephoneNo = null;
            String telephoneNo = null;

            DecimalFormat decimalFormat = new DecimalFormat("#");
            if (row.getColumnValue("行政区代码") != null)
                districtCode = decimalFormat.format(row.getColumnValue("行政区代码"));
            if (row.getColumnValue("电话号码") != null)
                telephoneNo = decimalFormat.format(row.getColumnValue("电话号码"));
            if (row.getColumnValue("手机号码") != null)
                mobilephoneNo = decimalFormat.format(row.getColumnValue("手机号码"));

            CarrierCreateDto createDto = CarrierCreateDto.builder()
                    .branchCode(userPrincipal.getCurrentBranchCode())
                    .build();
            createDto.setCode(code);
            createDto.setName(name);
            // TODO: 2016/11/14 需验证类型是否存在
            createDto.setType(CarrierDto.CarrierType.valueOf(type.trim()));
            createDto.setSettleCycle(CarrierDto.SettleCycle.valueOf(settleCycle.trim()));
            createDto.setPaymentType(CarrierDto.PaymentType.valueOf(paymentType.trim()));
            createDto.setCalculateType(CarrierDto.CalculateType.valueOf(calculateType.trim()));
            createDto.setTransportType(CarrierDto.TransportType.valueOf(transportType.trim()));
            createDto.setContactMan(contactMan);
            createDto.setMobilephoneNo(mobilephoneNo);
            createDto.setTelephoneNo(telephoneNo);
            createDto.setDistrictCode(districtCode);
            createDto.setAddress(address);
            createDto.setRemark(remark);
            createDto.setIsActive(true);

            return createDto;
        });

        return carrierService.batchCreate(createModels);
    }
}
