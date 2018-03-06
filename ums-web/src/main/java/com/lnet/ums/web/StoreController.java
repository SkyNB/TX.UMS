package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.core.SelectObject;
import com.lnet.framework.excel.reader.ExcelReader;
import com.lnet.framework.excel.reader.ExcelRow;
import com.lnet.framework.excel.util.ExcelFormat;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.ums.contract.api.StoreService;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerEntity.Store;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/store")
public class StoreController {
    @Resource
    UserPrincipalImpl userPrincipal;
    @Resource
    private StoreService storeService;
    @Resource
    private CustomerService customerService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        Response<List<Customer>> resp = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<SelectObject> res = new ArrayList<>();
        if (resp.isSuccess())
            res = resp.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customer", res);
        return "store/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody KendoGridRequest params) {
        return storeService.pageList(params.getPage(),params.getPageSize(),params.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        Response<List<Customer>> resp = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<SelectObject> res = new ArrayList<>();
        if (resp.isSuccess())
            res = resp.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customer", res);
        return "store/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody Store store) {
        store.setActive(true);
        return storeService.create(store);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String edit(ModelMap map) {
        Response<List<Customer>> resp = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<SelectObject> res = new ArrayList<>();
        if (resp.isSuccess())
            res = resp.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());

        map.addAttribute("customer", res);
        return "store/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody Store store) {
        return storeService.update(store);
    }

    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response getByCode(@PathVariable String code) {
        return storeService.getByCode(code);
    }

    @RequestMapping(value = "/forbidden/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response forbidden(@PathVariable String code) {
        return storeService.inactivate(code);
    }

    @RequestMapping(value = "/invocation/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response invocation(@PathVariable String code) {
        return storeService.activate(code);
    }

    @RequestMapping(value = "/getByOwnerCode/{code}", method = RequestMethod.GET)
    @ResponseBody
    public List<Store> getByOwnerCode(@PathVariable String code) {
        Response<List<Store>> resp = storeService.getByOwnerCode(code);
        if (resp.isSuccess()) {
            return resp.getBody();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/getBranchAvailable", method = RequestMethod.GET)
    @ResponseBody
    public List<Store> getBranchAvailable() {
        Response<List<Store>> resp = storeService.getBranchAvailable(userPrincipal.getCurrentBranchCode());
        if (resp.isSuccess()) {
            return resp.getBody();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importStore() {
        return "store/import";
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

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<Response>> importStore(@RequestParam("storeFile") MultipartFile file) {
        try {
            List<Store> stores = ExcelReader.readByColumnName(file.getInputStream(), ExcelFormat.OFFICE2007, 0, 0, new Function<ExcelRow, Store>() {
                @Override
                public Store apply(ExcelRow row) {
                    String code = (String) row.getColumnValue("编码");
                    String name = (String) row.getColumnValue("名称");
                    String ownerCode = (String) row.getColumnValue("客户编码");
                    String contactMan = (String) row.getColumnValue("联系人");
                    //String telPhoneNo = (String) row.getColumnValue("电话号");
                    String brandCodes = (String) row.getColumnValue("品牌编码");
                    //String address = (String) row.getColumnValue("详细地址");
                    String remark = (String) row.getColumnValue("备注");

                    String mobilePhoneNo = null;
                    String telPhoneNo = null;
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    //String districtCode = decimalFormat.format(row.getColumnValue("行政区代码"));
                    //String mobilePhoneNo = decimalFormat.format(row.getColumnValue("手机号"));

                    if (null != row.getColumnValue("电话号"))
                        telPhoneNo = decimalFormat.format(row.getColumnValue("电话号"));
                    if (null != row.getColumnValue("手机号"))
                        mobilePhoneNo = decimalFormat.format(row.getColumnValue("手机号"));

                    Store store = Store.builder()
                            .code(code)
                            .name(name)
                            .ownerCode(ownerCode)
                            .contactMan(contactMan)
                            .mobilePhoneNo(mobilePhoneNo)
                            .telephoneNo(telPhoneNo)
                            .brands(brandCodes)
                            //.districtCode(districtCode)
                            //.address(address)
                            .remark(remark)
                            .build();

                    //填充地址信息
                    /*Response<List<District>> response = districtService.getSuperior(districtCode.toString());
                    if (response.isSuccess()) {
                        List<District> districts = response.getBody();
                        districts.forEach(ele -> {
                            switch (ele.getType()) {
                                case PROVINCE:
                                    store.setProvince(ele.getName());
                                    break;
                                case CITY:
                                    store.setCity(ele.getName());
                                    break;
                                case AREA:
                                    store.setArea(ele.getName());
                                    break;
                                case STREET:
                                    store.setStreet(ele.getName());
                                    break;
                                default:
                                    break;
                            }
                        });
                    }*/

                    return store;
                }
            });

            return storeService.batchInsert(stores);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}
