package com.lnet.ums.rest.api.Impl;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.excel.reader.ExcelReader;
import com.lnet.framework.excel.util.ExcelFormat;
import com.lnet.model.ums.customer.customerEntity.BusinessGroup;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.ums.contract.api.BusinessGroupService;
import com.lnet.ums.contract.api.CustomerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomerApplication {
    @Resource
    private CustomerService customerService;
    @Resource
    private BusinessGroupService businessGroupService;

    public Object pageList(KendoGridRequest params) {
        return customerService.pageList(params.getPage(), params.getPageSize(), params.getParams());
    }

    public Response create(Customer customer) {
        customer.setActive(true);
        return customerService.create(customer);
    }

    public Response update(Customer customer) {
        return customerService.update(customer);
    }

    public Response get(String rowId) {
        return customerService.get(rowId);
    }

    public Response getByCode(String code) {
        return customerService.getByCode(code);
    }

    public Response<List<Customer>> getAvailable() {
        return customerService.getAvailable();
    }

    public Response forbidden(String code) {
        return customerService.inactivate(code);
    }

    public Response invocation(String code) {
        return customerService.activate(code);
    }

    public Response<List<Customer>> getByBizGroupCode(String code) {
        return customerService.getByBizGroupCode(code);
    }

    public Response<Customer> getByCodeAndBrandsLimit(String code) {
        return customerService.getByCodeAndBrandsLimit(code);
    }

    public ListResponse<Customer> findCustomerForBranch(String branchCode) {
        return customerService.findCustomerForBranch(branchCode);
    }

    public Response<List<Response>> importCustomer(InputStream inputStream, String userId) {
        try {
            Response<List<BusinessGroup>> businessGroupResponse = businessGroupService.getAll();
            if (!businessGroupResponse.isSuccess()) {
                return ResponseBuilder.fail("获取业务组信息失败，无法验证数据的业务组信息");
            }
            //客户级别
            List<String> rating = new ArrayList<>();
            rating.add("A");
            rating.add("B");
            rating.add("C");
            List<Response> result = new ArrayList<>();
            Set<String> customerCodeSet = new HashSet<String>();
            List<Customer> customers = ExcelReader.readByColumnName(inputStream, ExcelFormat.OFFICE2007, 0, 0, row -> {
                Response response = ResponseBuilder.success(null, "");
                result.add(response);
                Customer customer = new Customer();
                customer.setCreateUserId(userId);

                String customerCode = verifyCustomerCode((String) row.getColumnValue("编码"));
                if (customerCode == null) {
                    response.setSuccess(false);
                    response.setMessage("编码为空或者格式错误");
                    return null;
                } else if (customerService.exists(customerCode)) {
                    response.setSuccess(false);
                    response.setMessage("该客户已存在");
                    return null;
                } else if (customerCodeSet.contains(customerCode)) {
                    response.setSuccess(false);
                    response.setMessage("导入数据中存在相同的客户编码");
                    return null;
                }
                customer.setCode(customerCode);

                String customerName = (String) row.getColumnValue("名称");
                if (customerName == null || customerName.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage("名称为空");
                    return null;
                }
                customer.setName(customerName);

                String customerRating = verifyRatingCode(rating, (String) row.getColumnValue("级别"));
                if (customerRating == null) {
                    response.setSuccess(false);
                    response.setMessage("级别为空或者填写有误");
                    return null;
                }
                customer.setRatingCode(customerRating);

                String customerBizGroup = verifyBizGroup(businessGroupResponse.getBody(), (String) row.getColumnValue("业务组"));
                if (customerBizGroup == null) {
                    response.setSuccess(false);
                    response.setMessage("业务组为空或者填写有误");
                    return null;
                }
                customer.setBizGroupCode(customerBizGroup);

                String contract = (String) row.getColumnValue("联系人");
                if (contract == null || contract.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage("联系人为空");
                    return null;
                }
                customer.setContactMan(contract);

                String contractPhone = verifyPhone((String) row.getColumnValue("联系电话"));
                if (contractPhone == null) {
                    response.setSuccess(false);
                    response.setMessage("联系电话为空或者格式错误");
                    return null;
                }
                customer.setContactPhone(contractPhone);

                String fullName = (String) row.getColumnValue("全名");
                if (!(fullName == null || fullName.isEmpty())) {
                    customer.setFullName(fullName);
                }

                String address = (String) row.getColumnValue("联系地址");
                if (address == null || address.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage("联系地址为空");
                    return null;
                }
                customer.setContactAddress(address);

                String remark = (String) row.getColumnValue("备注");
                if (!(remark == null || remark.isEmpty())) {
                    customer.setRemark(remark);
                }

                customerCodeSet.add(customerCode);
                return customer;
            });
            customers.removeIf(customer -> customer == null);
            if (customers.size() > 0) {
                Response<List<Customer>> response = customerService.batchCreate(customers);
                if (response.isSuccess()) {
                    Response resultResponse = ResponseBuilder.success(result);
                    resultResponse.setMessage("成功导入" + customers.size() + "条记录");
                    return resultResponse;
                }
            }
            Response resultResponse = ResponseBuilder.success(result);
            if (result.size() > 0) {
                resultResponse.setSuccess(false);
                resultResponse.setMessage("创建失败");
            }
            return resultResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.fail(e);
        }
    }

    //验证客户编码
    private String verifyCustomerCode(String customerCode) {
        String customerCodeRegex = "^[A-Za-z0-9]+$";
        if (customerCode != null && customerCode.matches(customerCodeRegex)) {
            return customerCode;
        }
        return null;
    }

    //验证客户级别
    private String verifyRatingCode(List<String> rating, String raingCode) {
        if (raingCode != null) {
            for (String rat : rating) {
                if (raingCode.equals(rat)) {
                    return raingCode;
                }
            }
        }
        return null;
    }

    //验证业务组
    private String verifyBizGroup(List<BusinessGroup> businessGroups, String bizGroup) {
        if (bizGroup != null && businessGroups != null) {
            for (BusinessGroup group : businessGroups) {
                if (group.getName().equals(bizGroup)) {
                    return group.getCode();
                }
            }
        }
        return null;
    }

    //验证电话号码
    private String verifyPhone(String phone) {
        String phoneRegex = "^[1][3,4,5,8][0-9]{9}$";
        String phoneRegex2 = "^[0][1-9]{2,3}-[0-9]{5,10}$";
        if (phone != null && (phone.matches(phoneRegex) || phone.matches(phoneRegex2))) {
            return phone;
        }
        return null;
    }
}
