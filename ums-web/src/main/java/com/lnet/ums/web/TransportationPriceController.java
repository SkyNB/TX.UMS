package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.SelectObject;
import com.lnet.model.ums.expense.ExpenseAccount;
import com.lnet.model.ums.transprotation.transprotationEntity.LogisticsOrder;
import com.lnet.ums.contract.api.CarrierService;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.ums.contract.api.ExpenseAccountService;
import com.lnet.ums.contract.api.VehicleTypeService;
import com.lnet.model.ums.carrier.carrierDto.CarrierDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierListDto;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerDto.PriceCalcDto;
import com.lnet.model.ums.customer.customerDto.PriceDto;
import com.lnet.ums.service.TransportationPriceService;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/9/23.
 */
@Controller
@RequestMapping("price")
public class TransportationPriceController {

    @Resource
    TransportationPriceService transportationPriceService;

    @Resource
    private VehicleTypeService vehicleTypeService;

    @Resource
    ExpenseAccountService expenseAccountService;

    @Resource
    CustomerService customerService;

    @Resource
    CarrierService carrierService;

    @Resource
    UserPrincipalImpl userPrincipal;

    @RequestMapping("")
    public String index(ModelMap map) {
        map.put("transportTypes", CarrierDto.TransportType.values());
        map.put("orderTypes", LogisticsOrder.OrderType.values());
        List<ExpenseAccount> expenseAccounts  = expenseAccountService.findAll().getBody();
        if(expenseAccounts!=null){
            map.put("exaccts",expenseAccounts.stream()
                    .map(m -> new SelectObject( m.getCode(), m.getName()))
                    .collect(Collectors.toList()));
        }
        return "price/index";
    }

    @RequestMapping("create")
    public String create(ModelMap map) {
        map.put("ownerTypes", PriceDto.OwnerType.values());
        map.put("transportTypes", CarrierDto.TransportType.values());
        map.put("orderTypes", LogisticsOrder.OrderType.values());
        map.put("calcAttrs", PriceDto.CalcAttr.values());
        map.put("calcFormulas", PriceDto.CalcFormula.values());
        map.put("vehicleTypes", vehicleTypeService.findAll().getBody());
        return "price/create";
    }

    @RequestMapping("calculate")
    public String calculate(ModelMap map) {
        map.put("transportTypes", CarrierDto.TransportType.values());
        map.put("calcAttrs", PriceDto.CalcAttr.values());
        map.put("vehicleTypes", vehicleTypeService.findAll().getBody());
        return "price/calculate";
    }

    @RequestMapping("update")
    public String update(ModelMap map) {
        map.put("ownerTypes", PriceDto.OwnerType.values());
        map.put("transportTypes", CarrierDto.TransportType.values());
        map.put("orderTypes", LogisticsOrder.OrderType.values());
        map.put("calcAttrs", PriceDto.CalcAttr.values());
        map.put("calcFormulas", PriceDto.CalcFormula.values());
        map.put("vehicleTypes", vehicleTypeService.findAll().getBody());
        return "price/update";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public
    @ResponseBody
    Object search(@RequestBody KendoGridRequest request) {
        request.setParams("branchCode", userPrincipal.getCurrentBranchCode());
        return transportationPriceService.search(request);
    }
    @RequestMapping(value = "getOwnerCode", method = RequestMethod.POST)
    public
    @ResponseBody
    Object getOwnerCode() {
        Response<List<Customer>> resp = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        Response<List<CarrierListDto>> carrierResponse = carrierService.getBranchAvailable(userPrincipal.getCurrentBranchCode());
        if (resp.isSuccess()&&carrierResponse.isSuccess()) {
            List<SelectObject> list = resp.getBody().stream()
                    .map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName()))
                    .collect(Collectors.toList());
            list.addAll(carrierResponse.getBody().stream()
                    .map(m -> new SelectObject(m.getCarrierId(), m.getCode(), m.getName()))
                    .collect(Collectors.toList()));
            return list;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public
    @ResponseBody
    Object create(@RequestBody PriceDto priceDto) {
        priceDto.setBranchCode(userPrincipal.getCurrentBranchCode());
        return transportationPriceService.create(priceDto);
    }

    @RequestMapping(value = "calculate", method = RequestMethod.POST)
    public
    @ResponseBody
    Object calculate(@RequestBody PriceDto priceDto) {
        return transportationPriceService.calculate(new PriceCalcDto(priceDto.getOwnerCode(), priceDto.getOrgin(),
                priceDto.getDestination(), priceDto.getCalcAttr().name(), priceDto.getTransportType(), priceDto.getProductCategory(),
                priceDto.getVehicleType(), new BigDecimal(priceDto.getRemark())));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Object update(@RequestBody PriceDto priceDto) {
        priceDto.setBranchCode(userPrincipal.getCurrentBranchCode());
        return transportationPriceService.update(priceDto);
    }

    @RequestMapping(value = "get/{priceId}", method = RequestMethod.GET)
    public
    @ResponseBody
    Object get(@PathVariable String priceId) {
        return transportationPriceService.get(priceId);
    }

    @RequestMapping(value = "findRanges/{priceId}", method = RequestMethod.POST)
    public
    @ResponseBody
    Object findRanges(@PathVariable String priceId) {
        return transportationPriceService.findRanges(priceId);
    }

    @RequestMapping(value = "import", method = RequestMethod.GET)
    public String importPrice() {
        return "price/import";
    }

    @RequestMapping(value = "importPrice", method = RequestMethod.POST)
    public
    @ResponseBody
    Object importOrder(@RequestParam("priceFile") MultipartFile file) throws IOException {
        return transportationPriceService.importPrice(file.getInputStream(), userPrincipal.getUserId(), userPrincipal.getCurrentBranchCode());
    }
}
