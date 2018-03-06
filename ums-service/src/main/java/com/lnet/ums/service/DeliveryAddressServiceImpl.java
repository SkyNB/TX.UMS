package com.lnet.ums.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.model.ums.customer.customerEntity.DeliveryAddress;
import com.lnet.ums.contract.api.DeliveryAddressService;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.ums.mapper.dao.CustomerDao;
import com.lnet.ums.mapper.dao.DeliveryAddressDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    private final String className = this.getClass().getSimpleName() + ".";

    @Autowired
    private DeliveryAddressDao deliveryAddressDao;
    @Autowired
    private CustomerDao customerDao;

    @Override
    public Response create(DeliveryAddress deliveryAddress) {
        try {
            Assert.notNull(deliveryAddress);

            deliveryAddress.setDeliveryAddressId(Snowflake.getInstance().next());
            boolean isSuccess = deliveryAddressDao.insert(deliveryAddress) > 0;
            if (isSuccess)
                return ResponseBuilder.success("创建成功！");
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            log.error(className + "create", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response update(DeliveryAddress deliveryAddress) {
        try {
            Assert.notNull(deliveryAddress);
            Assert.hasText(deliveryAddress.getDeliveryAddressId());

            boolean isSuccess = deliveryAddressDao.update(deliveryAddress) > 0;
            if (isSuccess)
                return ResponseBuilder.success("修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            log.error(className + "update", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<DeliveryAddress>> getAll() {
        try {
            List<DeliveryAddress> list = deliveryAddressDao.getAll();

            Assert.notEmpty(list, "获取数据失败！");

            return ResponseBuilder.success(list);
        } catch (Exception e) {
            log.error(className + "getAll", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<DeliveryAddress>> getAvailable() {
        try {
            List<DeliveryAddress> list = deliveryAddressDao.getAvailable();
            Assert.notEmpty(list, "获取数据失败！");

            return ResponseBuilder.success(list);
        } catch (Exception e) {
            log.error(className + "getAvailable", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<DeliveryAddress> get(String deliveryAddressId) {
        try {
            Assert.hasText(deliveryAddressId);

            DeliveryAddress deliveryAddress = deliveryAddressDao.get(deliveryAddressId);
            Assert.notNull(deliveryAddress, "获取数据失败！");

            return ResponseBuilder.success(deliveryAddress);
        } catch (Exception e) {
            log.error(className + "get", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<DeliveryAddress>> getByOwnerCode(String ownerCode) {
        try {
            Assert.hasText(ownerCode);

            List<DeliveryAddress> list = deliveryAddressDao.getByOwnerCode(ownerCode);
            Assert.notEmpty(list, "获取数据失败！");

            return ResponseBuilder.success(list);
        } catch (Exception e) {
            log.error(className + "getByOwnerCode", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String deliveryAddressId) {
        try {
            Assert.hasText(deliveryAddressId);

            boolean isSuccess = deliveryAddressDao.inactivate(deliveryAddressId) > 0;
            if (isSuccess)
                return ResponseBuilder.success("禁用成功！");
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error(className + "inactivate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String deliveryAddressId) {
        try {
            Assert.hasText(deliveryAddressId);

            boolean isSuccess = deliveryAddressDao.activate(deliveryAddressId) > 0;
            if (isSuccess)
                return ResponseBuilder.success("启用成功！");
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error(className + "activate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<DeliveryAddress> pageList(int pageNo, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(pageNo);
            Assert.notNull(pageSize);

            PageHelper.startPage(pageNo, pageSize);
            List<DeliveryAddress> list = deliveryAddressDao.pageList(params);
            List<Customer> customers = customerDao.getAll();

            //填充属性
            list.forEach(ele -> {
                Optional<Customer> optional = customers.stream().filter(f -> f.getCode().equals(ele.getOwnerCode())).findFirst();
                if (optional.isPresent())
                    ele.setOwnerName(optional.get().getName());
            });

            PageInfo<DeliveryAddress> pageInfo = new PageInfo<>(list);
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return  ResponseBuilder.pageFail(e);
        }
    }

    @Override
    public Response<List<DeliveryAddress>> findByOwnerCode(String ownerCode) {
        return ResponseBuilder.success(deliveryAddressDao.getByOwnerCode(ownerCode));
    }
}
