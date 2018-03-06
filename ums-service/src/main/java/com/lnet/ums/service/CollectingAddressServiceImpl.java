package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.CollectingAddressService;
import com.lnet.model.ums.customer.customerEntity.CollectingAddress;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.ums.mapper.dao.CollectingAddressDao;
import com.lnet.ums.mapper.dao.CustomerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
public class CollectingAddressServiceImpl implements CollectingAddressService {
    private final String className = this.getClass().getSimpleName() + ".";

    @Autowired
    private CollectingAddressDao collectingAddressDao;
    @Autowired
    private CustomerDao customerDao;

    @Override
    public boolean exists(String code, String ownerCode, String collectingAddressId) throws Exception {
        try {
            Assert.hasText(code);
            Assert.hasText(ownerCode);

            return collectingAddressDao.exists(code, ownerCode, collectingAddressId);
        } catch (Exception e) {
            log.error(className + "exists", e.getMessage());
            throw e;
        }
    }

    @Override
    public Response create(CollectingAddress collectingAddress) {
        try {
            Assert.notNull(collectingAddress);
            Assert.hasText(collectingAddress.getCode());
            Assert.hasText(collectingAddress.getOwnerCode());

            boolean isExists = this.exists(collectingAddress.getCode(), collectingAddress.getOwnerCode(), null);
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            collectingAddress.setCollectingAddressId(Snowflake.getInstance().next());

            boolean isSuccess = collectingAddressDao.insert(collectingAddress) > 0;
            if (isSuccess)
                return ResponseBuilder.success("创建成功！");
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response batchInsert(List<CollectingAddress> collectingAddresses) {
        try {
            //参数检验
            Response isPass = this.validate(collectingAddresses);

            if (!isPass.isSuccess())
                return isPass;

            //完善collectingAddress信息
            collectingAddresses.forEach(ele -> {
                ele.setCollectingAddressId(Snowflake.getInstance().next());
            });

            collectingAddressDao.batchInsert(collectingAddresses);
//            Assert.isTrue(collectingAddressDao.batchInsert(collectingAddresses) == collectingAddresses.size(), "批量添加失败！");
            return ResponseBuilder.success("", "批量添加成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "batchInsert", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response update(CollectingAddress collectingAddress) {
        try {
            Assert.notNull(collectingAddress);
            Assert.hasText(collectingAddress.getCode());
            Assert.hasText(collectingAddress.getOwnerCode());
            Assert.hasText(collectingAddress.getCollectingAddressId());

            boolean isExists = this.exists(collectingAddress.getCode(), collectingAddress.getOwnerCode(), collectingAddress.getCollectingAddressId());
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            boolean isSuccess = collectingAddressDao.update(collectingAddress) > 0;
            if (isSuccess)
                return ResponseBuilder.success("修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<CollectingAddress>> getAll() {
        try {
            List<CollectingAddress> list = collectingAddressDao.getAll();

            Assert.notNull(list, "获取数据失败！");

            return ResponseBuilder.success(list);
        } catch (Exception e) {
            log.error(className + "getAll", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<CollectingAddress>> getAvailable() {
        try {
            List<CollectingAddress> list = collectingAddressDao.getAvailable();

            Assert.notNull(list, "获取数据失败！");

            return ResponseBuilder.success(list);
        } catch (Exception e) {
            log.error(className + "getAvailable", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<CollectingAddress> get(String collectingAddressId) {
        try {
            CollectingAddress ele = collectingAddressDao.get(collectingAddressId);

            Assert.notNull(ele, "获取数据失败！");

            return ResponseBuilder.success(ele);
        } catch (Exception e) {
            log.error(className + "get", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<CollectingAddress> getByCode(String code) {
        try {
            CollectingAddress ele = collectingAddressDao.getByCode(code);

            Assert.notNull(ele, "获取数据失败！");

            return ResponseBuilder.success(ele);
        } catch (Exception e) {
            log.error(className + "getByCode", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }


    @Override
    public Response<List<CollectingAddress>> getByOwnerCode(String ownerCode) {
        try {
            Assert.hasText(ownerCode);

            List<CollectingAddress> list = collectingAddressDao.getByOwnerCode(ownerCode);
            Assert.notEmpty(list, "获取数据失败！");

            return ResponseBuilder.success(list);
        } catch (Exception e) {
            log.error(className + "getByOwnerCode", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            boolean isSuccess = collectingAddressDao.inactivate(code) > 0;
            if (isSuccess)
                return ResponseBuilder.success("禁用成功！");
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error(className + "inactivate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String code) {
        try {
            boolean isSuccess = collectingAddressDao.activate(code) > 0;
            if (isSuccess)
                return ResponseBuilder.success("启用成功！");
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error(className + "activate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<CollectingAddress> pageList(int pageNo, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(pageNo);
            Assert.notNull(pageSize);

            PageHelper.startPage(pageNo, pageSize);
            List<CollectingAddress> list = collectingAddressDao.pageList(params);
            List<Customer> customers = customerDao.getAll();

            //填充属性
            list.forEach(ele -> {
                Optional<Customer> optional = customers.stream().filter(f -> f.getCode().equals(ele.getOwnerCode())).findFirst();
                if (optional.isPresent())
                    ele.setOwnerName(optional.get().getName());
            });
            PageInfo<CollectingAddress> pageInfo = new PageInfo<>(list);
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "activate", e.getMessage());
            return (PageResponse) ResponseBuilder.fail(e.getMessage());
        }
    }

    //参数检验
    private Response validate(List<CollectingAddress> collectingAddresses) {
        try {
            Assert.notEmpty(collectingAddresses, "空数据！");

            //属性检验
            collectingAddresses.forEach(ele -> {
                //非空检验
                Assert.hasText(ele.getCode(), "编码不能为空！");
                Assert.hasText(ele.getName(), "名称不能为空！");
                Assert.hasText(ele.getOwnerCode(), "客户编码不能为空！");
                Assert.hasText(ele.getType().toString(), "类型不能为空！");
                Assert.hasText(ele.getContactMan(), "联系人不能为空！");
                Assert.hasText(ele.getMobilePhoneNo(), "手机号不能为空");
                Assert.hasText(ele.getDistrictCode(), "行政区代码不能为空");
                Assert.hasText(ele.getAddress(), "详细地址不能为空！");

                //地址类型检验 Type:WAREHOUSE|FACTORY
                {
                    boolean isFit = false;
                    for (CollectingAddress.typeEnum typeEnum : CollectingAddress.typeEnum.values()) {
                        if (typeEnum.toString().equals(ele.getType().toString()))
                            isFit = true;
                    }
                    Assert.isTrue(isFit, "类型错误！");
                }
            });

            //编码检验：同一个客户编码只能出现一次
            {
                List<String> errorCode = new ArrayList<>();
                Map<String, List<CollectingAddress>> ownerMap = collectingAddresses.stream().collect(Collectors.groupingBy(CollectingAddress::getOwnerCode));
                ownerMap.entrySet().forEach(ele -> {
                    Map<String, List<CollectingAddress>> map = ele.getValue().stream().collect(Collectors.groupingBy(CollectingAddress::getCode));
                    map.entrySet().forEach(e -> {
                        if (1 < e.getValue().size())
                            errorCode.add(ele.getKey() + ":" + e.getKey());
                    });
                });
                if (null != errorCode && 0 < errorCode.size())
                    return ResponseBuilder.fail(String.join(",", errorCode) + "编码重复（编码：客户编码)，请更正后导入！");
            }

            return ResponseBuilder.success();
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}
