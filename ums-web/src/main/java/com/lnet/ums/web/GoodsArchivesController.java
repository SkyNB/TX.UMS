package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.core.SelectObject;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.ums.contract.api.GoodsArchivesService;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerEntity.GoodsArchives;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/goodsArchives")
public class GoodsArchivesController {
    @Resource
    GoodsArchivesService goodsArchivesService;
    @Resource
    CustomerService customerService;
    @Autowired
    ServletContext servletContext;
    @Resource
    UserPrincipalImpl userPrincipal;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        Response<List<Customer>> customers = customerService.findCustomerForBranch(userPrincipal.getCurrentBranchCode());
        List<SelectObject> cusObject = new ArrayList<>();
        if (customers.isSuccess()) {
            cusObject = customers.getBody().stream().map(m -> new SelectObject(m.getCustomerId(), m.getCode(), m.getName())).collect(Collectors.toList());
        }
        map.addAttribute("customers", cusObject);
        map.addAttribute("categoryEnum", GoodsArchives.categoryEnum.values());
        return "goodsArchives/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody KendoGridRequest request) {
        Map<String, Object> filterMap = request.getParams();
        filterMap.put("branchCode", userPrincipal.getCurrentBranchCode());
        return goodsArchivesService.pageList(request.getPage(), request.getPageSize(), filterMap);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        map.addAttribute("categoryEnum", GoodsArchives.categoryEnum.values());
        return "goodsArchives/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody GoodsArchives goodsArchives) {
        goodsArchives.setBranchCode(userPrincipal.getCurrentBranchCode());
        return goodsArchivesService.create(goodsArchives);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String edit(ModelMap map) {
        map.addAttribute("categoryEnum", GoodsArchives.categoryEnum.values());
        return "goodsArchives/update";
    }

    @RequestMapping(value = "get/{archivesId}", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<GoodsArchives> get(@PathVariable String archivesId) {
        try {
            return goodsArchivesService.get(archivesId);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<GoodsArchives> update(@RequestBody GoodsArchives goodsArchives) {
        try {
            return goodsArchivesService.update(goodsArchives);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Response delete(@RequestBody List<String> archivesIds) {
        try {
            return goodsArchivesService.batchDelete(archivesIds);
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String goodsArchivesImport() {
        return "goodsArchives/import";
    }

    @RequestMapping(value = "/downLoad", method = RequestMethod.GET)
    public void downLoad(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        InputStream input = new FileInputStream(servletContext.getRealPath("/downloads/" + fileName));
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int length;
        while ((length = input.read(bytes)) > 0) {
            out.write(bytes, 0, length);
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Object goodsArchivesImport(@RequestParam("goodsArchivesFile") MultipartFile file) {
        try {
            return goodsArchivesService.importGoodsArchives(file.getInputStream(), userPrincipal.getCurrentBranchCode());
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}
