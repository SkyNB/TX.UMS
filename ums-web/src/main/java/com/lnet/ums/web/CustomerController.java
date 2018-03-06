package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.core.SelectObject;
import com.lnet.ums.contract.api.BusinessGroupService;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.model.ums.customer.customerEntity.Brand;
import com.lnet.model.ums.customer.customerEntity.Customer;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    private final String[] ratingCode = new String[]{"A", "B", "C"};

    @Resource
    private CustomerService customerService;
    @Resource
    private BusinessGroupService businessGroupService;
    @Autowired
    private ServletContext servletContext;
    @Resource
    UserPrincipalImpl userPrincipal;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.addAttribute("ratingCodes", ratingCode);
        return "customer/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public
    @ResponseBody
    Object search(@RequestBody KendoGridRequest params) {
        return customerService.pageList(params.getPage(),params.getPageSize(),params.getParams());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        map.addAttribute("businessGroups", businessGroupService.getAll().getBody());
        map.addAttribute("ratingCodes", ratingCode);
        return "customer/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    Response create(@RequestBody Customer customer) {
        customer.setActive(true);
        return customerService.create(customer);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(ModelMap map) {
        map.addAttribute("businessGroups", businessGroupService.getAll().getBody());
        map.addAttribute("ratingCodes", ratingCode);
        return "customer/update";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @RequestMapping(value = "/get/{rowId}", method = RequestMethod.POST)
    @ResponseBody
    public Response get(@PathVariable String rowId) {
        return customerService.get(rowId);
    }

    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response getByCode(@PathVariable String code) {
        return customerService.getByCode(code);
    }

    @RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> getAvailable() {
        return customerService.getAvailable().getBody();
    }

    @RequestMapping(value = "/getAvailableForSelect", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectObject> getAvailableForSelect() {
        Response<List<Customer>> resp = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        if (resp.isSuccess()) {
            return resp.getBody().stream()
                    .map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName()))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/getBrands/{code}", method = RequestMethod.POST)
    @ResponseBody
    public List<SelectObject> getBrands(@PathVariable String code) {
        Response<Customer> reps = customerService.getByCodeAndBrandsLimit(code);
        List<SelectObject> rep = null;
        if (reps.isSuccess()) {
            List<Brand> brands = reps.getBody().getBrands();
            if (null != brands)
                rep = brands.stream().map(m -> new SelectObject(m.getRowId(), m.getCode(), m.getName())).collect(Collectors.toList());
        }
        return rep;
    }

    @RequestMapping(value = "/forbidden/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response forbidden(@PathVariable String code) {
        return customerService.inactivate(code);
    }

    @RequestMapping(value = "/invocation/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response invocation(@PathVariable String code) {
        return customerService.activate(code);
    }

    @RequestMapping(value = "/getByBizGroupCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<Customer>> getByBizGroupCode(@PathVariable String code) {
        return customerService.getByBizGroupCode(code);
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
    public String importVehicle(){
        return "customer/import";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Object importVehicle(@RequestParam("customerFile") MultipartFile file) {
        try {
            return customerService.importCustomer(file.getInputStream(), userPrincipal.getUserId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseBuilder.fail(e);
        }
    }
}
