package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.CustomerService;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerEntity.Project;
import com.lnet.ums.mapper.dao.BrandDao;
import com.lnet.ums.mapper.dao.CustomerDao;
import com.lnet.ums.mapper.dao.ProjectDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final String className = this.getClass().getSimpleName() + ".";
    @Resource
    private CustomerDao customerDao;
    @Resource
    private BrandDao brandDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public Response<Customer> get(String customerId) {
        try {
            Assert.hasText(customerId);

            Customer customer = customerDao.get(customerId);
            if (customer != null) {
                customer.setBrands(brandDao.getByCustomerCode(customer.getCode()));
                return ResponseBuilder.success(customer);
            }
            return ResponseBuilder.fail("客户不存在！");
        } catch (Exception e) {
            log.error(className + "get", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Customer> getByCode(String code) {
        try {
            Assert.hasText(code);

            Customer customer = customerDao.getByCode(code);
            if (customer != null) {
                customer.setBrands(brandDao.getByCustomerCode(code));
                return ResponseBuilder.success(customer);
            }
            return ResponseBuilder.fail("客户不存在！");
        } catch (Exception e) {
            log.error(className + "getByCode", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Customer> getByCodeAndBrandsLimit(String code) {
        try {
            Assert.hasText(code);

            Customer customer = customerDao.getByCode(code);
            if (customer != null) {
                customer.setBrands(brandDao.getByCustomerCodeAndActive(code));
                return ResponseBuilder.success(customer);
            }
            return ResponseBuilder.fail("客户不存在！");
        } catch (Exception e) {
            log.error(className + "getByCode", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public boolean exists(String code) {
        try {
            Assert.hasText(code);
            return customerDao.exists(code, null);
        } catch (Exception e) {
            log.error(className + "exists", e);
            return false;
        }
    }

    @Override
    public Response<Customer> create(Customer customer) {
        try {
            Assert.notNull(customer);
            Assert.hasText(customer.getCode());
            Assert.hasText(customer.getName());

            //是否存在
            if (exists(customer.getCode()))
                return ResponseBuilder.fail("客户编码已存在！");

            customer.setCustomerId(Snowflake.getInstance().next());
            //添加品牌
            setBrands(customer);
            customer.setCreateTime(LocalDateTime.now());

            boolean isSuccess = customerDao.insert(customer) > 0;
            if (customer.getBrands() != null && customer.getBrands().size() > 0) {
                brandDao.batchInsert(customer.getBrands());
            }
            if (isSuccess)
                return ResponseBuilder.success(customer, "创建成功！");
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Customer>> batchCreate(List<Customer> customers) {
        try {
            Assert.notEmpty(customers);
            customers.forEach(customer -> {
                customer.setCustomerId(Snowflake.getInstance().next());
                customer.setCreateTime(LocalDateTime.now());
                customer.setActive(true);
            });
            Assert.isTrue(customerDao.batchInsert(customers) == customers.size());
            return ResponseBuilder.list(customers);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "batchCreate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String code) {
        try {
            Assert.hasText(code);
            customerDao.activate(code);
            return ResponseBuilder.success("启用成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "activate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            Assert.hasText(code);
            customerDao.inactivate(code);
            return ResponseBuilder.success("禁用成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "inactivate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Customer> update(Customer customer) {
        try {
            Assert.notNull(customer);
            Assert.hasText(customer.getCode());
            Assert.hasText(customer.getName());

            customer.setModifyTime(LocalDateTime.now());
            //添加品牌
            setBrands(customer);

            brandDao.deleteByCustomerCode(customer.getCode());

            if (customer.getBrands() != null && customer.getBrands().size() > 0) {
                brandDao.batchInsert(customer.getBrands());
            }
            boolean isSuccess = customerDao.update(customer) > 0;
            if (isSuccess)
                return ResponseBuilder.success(customer, "修改成功！");
            return ResponseBuilder.fail("修改失败！");

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Customer>> getAll() {
        try {
            return ResponseBuilder.success(customerDao.getAll());
        } catch (Exception e) {
            log.error(className + "getAll", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Customer>> getAvailable() {
        try {
            return ResponseBuilder.success(customerDao.getAvailable());
        } catch (Exception e) {
            log.error(className + "getAvailable", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<Customer> pageList(int startPage, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(startPage);
            Assert.notNull(pageSize);

            PageHelper.startPage(startPage, pageSize);
            PageInfo<Customer> pageInfo = new PageInfo<>(customerDao.pageList(params));
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e);
            return (PageResponse) ResponseBuilder.fail(e);
        }
    }

    @Override
    public boolean batchInactivate(List<String> ids) {
        try {
            Assert.notNull(ids);

            boolean isSuccess = false;
            //SQL IN Clause 1000 item limit
            final int boundary = 1000;
            if (ids != null && ids.size() >= boundary) {
                int tmpResult = 0;
                for (int i = 0; i <= ids.size() / boundary; i++) {
                    int limitSize = ids.size() > i * boundary ? boundary : ids.size() - (i * boundary);
                    List<String> tmpIds = ids.stream()
                            .skip(i * boundary)
                            .limit(limitSize).collect(Collectors.toList());
                    if (tmpIds != null && tmpIds.size() > 0) {
                        tmpResult += customerDao.batchInactivate(tmpIds);
                    }
                }
                isSuccess = tmpResult >= ids.size();
            } else {
                isSuccess = customerDao.batchInactivate(ids) >= ids.size();
            }
            return isSuccess;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "batchInactivate", e);
            return false;
        }
    }

    /**
     * 添加品牌
     *
     * @param customer
     */
    private void setBrands(Customer customer) {
        if (customer.getBrands() != null) {
            customer.getBrands().forEach(brand -> {
                brand.setRowId(Snowflake.getInstance().next());
                brand.setCustomerCode(customer.getCode());
                brand.setCustomerName(customer.getName());
            });
        }
    }

    @Override
    public Response<List<Customer>> getByBizGroupCode(String bizGroupCode) {
        try {
            return ResponseBuilder.success(customerDao.getByBizGroupCode(bizGroupCode));
        } catch (Exception e) {
            log.error(className + "getByBizGroupCode", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public ListResponse<Customer> findCustomerForBranch(String branchCode) {
        try {
            final String errorParam = "参数不合法！";
            if (StringUtils.isBlank(branchCode))
                return ResponseBuilder.listFail(errorParam);

            //分公司所有可用的项目信息
            List<Project> projects = projectDao.getBranchAvailable(branchCode);

            //根据项目信息(projects)得到所需的客户编码customerCode
            List<String> customerCodes = projects.stream().map(Project::getCustomerCode).collect(Collectors.toList());

            //查询所有符合条件的客户信息
            List<Customer> customers = new ArrayList<>();
            if (null != customerCodes && 0 < customerCodes.size())
                customers = customerDao.getByCodes(customerCodes);

            return ResponseBuilder.list(customers);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.listFail(e.getMessage());
        }
    }

    @Override
    public Object importCustomer(InputStream inputStream, String userId) {
        // TODO: 2017/1/5
        return null;
    }

}
