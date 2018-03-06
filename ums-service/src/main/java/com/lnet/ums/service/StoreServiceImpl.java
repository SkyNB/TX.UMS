package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.StoreService;
import com.lnet.model.ums.customer.customerEntity.Brand;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerEntity.Store;
import com.lnet.model.ums.customer.customerEntity.StoreBrand;
import com.lnet.ums.mapper.dao.BrandDao;
import com.lnet.ums.mapper.dao.CustomerDao;
import com.lnet.ums.mapper.dao.StoreBrandDao;
import com.lnet.ums.mapper.dao.StoreDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    private final String className = this.getClass().getSimpleName() + ".";
    @Resource
    private StoreDao storeDao;
    @Resource
    private StoreBrandDao storeBrandDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private BrandDao brandDao;

    @Override
    public Response<Store> get(String storeId) {
        try {
            Assert.hasText(storeId);

            return ResponseBuilder.success(storeDao.get(storeId));
        } catch (Exception e) {
            log.error(className + "get", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Store> getByCode(String code) {
        try {
            Assert.hasText(code);

            Store store = storeDao.getByCode(code);
            store.setBrandCodes(storeBrandDao.getBrandCodes(store.getOwnerCode(), store.getCode()));

            return ResponseBuilder.success(store);
        } catch (Exception e) {
            log.error(className + "getByCode", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Store> create(Store store) {
        try {
            this.AssertStore(store);

            if (storeDao.exists(store.getOwnerCode(), store.getCode(), null))
                return ResponseBuilder.fail("该客户已存在此门店编码！");

            List<StoreBrand> storeBrands = this.addStoreBrands(store);

            store.setStoreId(Snowflake.getInstance().next());

            boolean isSuccess = storeDao.insert(store) > 0;

            if (storeBrands != null && storeBrands.size() > 0) {
                Assert.isTrue(storeBrandDao.batchInsert(storeBrands) >= storeBrands.size());
            }

            if (isSuccess)
                return ResponseBuilder.success(store, "创建成功！");
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Store> update(Store store) {
        try {
            this.AssertStore(store);

            if (storeDao.exists(store.getOwnerCode(), store.getCode(), store.getStoreId()))
                return ResponseBuilder.fail("该客户已存在此门店编码!");

            List<StoreBrand> storeBrands = this.addStoreBrands(store);

            storeBrandDao.deleteByStoreCodeAndCustomerCode(store.getCode(), store.getOwnerCode());
            if (storeBrands != null && storeBrands.size() > 0) {
                Assert.isTrue(storeBrandDao.batchInsert(storeBrands) >= storeBrands.size());
            }

            boolean isSuccess = storeDao.update(store) > 0;
            if (isSuccess)
                return ResponseBuilder.success(store, "修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public boolean batchDelete(List<String> storeIds) {
        try {
            Assert.notNull(storeIds);

            boolean isSuccess = false;
            //SQL IN Clause 1000 item limit
            final int boundary = 1000;
            if (storeIds != null && storeIds.size() >= boundary) {
                int tmpResult = 0;
                for (int i = 0; i <= storeIds.size() / boundary; i++) {
                    int limitSize = storeIds.size() > i * boundary ? boundary : storeIds.size() - (i * boundary);
                    List<String> tmpIds = storeIds.stream()
                            .skip(i * boundary)
                            .limit(limitSize).collect(Collectors.toList());
                    if (tmpIds != null && tmpIds.size() > 0) {
                        tmpResult += storeDao.batchDelete(tmpIds);
                    }
                }
                isSuccess = tmpResult >= storeIds.size();
            } else {
                isSuccess = storeDao.batchDelete(storeIds) >= storeIds.size();
            }

            return isSuccess;

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "batchDelete", e);
            return false;
        }
    }

    @Override
    public Response<List<Response>> batchInsert(List<Store> stores) {
        try {
            if ((null == stores) || (0 == stores.size())) {
                return ResponseBuilder.fail("参数为空！");
            }

            //验证参数
            Response validate = this.validate(stores);
            if (!validate.isSuccess())
                return validate;

            //完善门店ID信息
            stores.forEach(f -> {
                f.setStoreId(Snowflake.getInstance().next());
                f.setActive(true);
            });

            //完善品牌信息
            List<StoreBrand> storeBrands = new ArrayList<>();
            stores.forEach(ele -> {
                if (ele.getBrands() != null) {
                    String[] brands = ele.getBrands().split(",");
                    for (String brand : brands) {
                        StoreBrand storeBrand = StoreBrand.builder()
                                .storeBrandId(Snowflake.getInstance().next())
                                .storeCode(ele.getCode())
                                .brandCode(brand)
                                .clientCode(ele.getOwnerCode())
                                .build();
                        storeBrands.add(storeBrand);
                    }
                }
            });

            //批量操作大数据量特殊处理(单次插入不大于1000条的数据)
            final int boundary = 1000;
            if (stores != null && stores.size() >= boundary) {
                for (int i = 0; i <= stores.size() / boundary; i++) {
                    int limitSize = stores.size() > i * boundary ? boundary : stores.size() - (i * boundary);
                    List<Store> tmpStores = stores.stream()
                            .skip(i * boundary)
                            .limit(limitSize).collect(Collectors.toList());
                    if (tmpStores != null && tmpStores.size() > 0) {
                        Assert.isTrue(storeDao.batchInsert(tmpStores) == tmpStores.size());
                    }
                }
            } else {
                Assert.isTrue(storeDao.batchInsert(stores) == stores.size());
            }

            stores.forEach(ele -> {
                storeBrandDao.deleteByStoreCodeAndCustomerCode(ele.getCode(), ele.getOwnerCode());
            });
            if (storeBrands != null && storeBrands.size() > 0) {
                Assert.isTrue(storeBrandDao.batchInsert(storeBrands) == storeBrands.size());
            }

            return ResponseBuilder.success(null, "批量添加成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "batchInsert", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Store>> getAvailable(String clientCode) {
        try {
            Assert.hasText(clientCode);

            List<Store> storeList = storeDao.getAvailable().stream()
                    .filter(f -> f.getOwnerCode().equals(clientCode)).collect(Collectors.toList());
            //storeList.forEach(f -> f.setBrands(String.join(",", f.getBrandNames())));

            return ResponseBuilder.success(storeList);
        } catch (Exception e) {
            log.error(className + "getAvailable", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Store>> getAvailable(String clientCode, final String brand) {
        try {
            Assert.hasText(clientCode);
            Assert.hasText(brand);

            List<Store> storeList = storeDao.getAvailable().stream()
                    .filter(f -> f.getOwnerCode().equals(clientCode) && f.getBrands().contains(brand))
                    .collect(Collectors.toList());

            //storeList.forEach(f -> f.setBrands(String.join(",", f.getBrandNames())));

            return ResponseBuilder.success(storeList);
        } catch (Exception e) {
            log.error(className + "getAvailable", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Store>> getBranchAvailable(String branchCode) {
        try {
            Assert.hasText(branchCode);

            List<Store> storeList = storeDao.getBranchAvailable(branchCode);
            //storeList.forEach(f -> f.setBrands(String.join(",", f.getBrandNames())));

            return ResponseBuilder.success(storeList);
        } catch (Exception e) {
            log.error(className + "getBranchAvailable", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Store>> getBranchAvailable(String branchCode, String clientCode) {
        try {
            Assert.hasText(branchCode);
            Assert.hasText(clientCode);

            List<Store> storeList = storeDao.getBranchAvailable(branchCode).stream()
                    .filter(f -> f.getOwnerCode().equals(clientCode))
                    .collect(Collectors.toList());

            //storeList.forEach(f -> f.setBrands(String.join(",", f.getBrandNames())));

            return ResponseBuilder.success(storeList);
        } catch (Exception e) {
            log.error(className + "getBranchAvailable", e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<List<Store>> getByOwnerCode(String ownerCode) {
        try {
            Assert.hasText(ownerCode);

            List<Store> stores = storeDao.getByOwnerCode(ownerCode);
            Assert.notEmpty(stores, "获取数据失败！");

            return ResponseBuilder.success(stores);
        } catch (Exception e) {
            log.error(className + "getByOwnerCode", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<Store> pageList(int startPage, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(startPage);
            Assert.notNull(pageSize);

            PageHelper.startPage(startPage, pageSize);

            List<Store> stores = storeDao.pageList(params);
            List<Store> result = stores;
            List<Customer> customers = customerDao.getAll();
            Assert.notEmpty(stores);

            //获取符合品牌条件的门店信息并过滤符合条件的门店
            if (null != params.get("brandName")) {
                result = new ArrayList<>();
                List<StoreBrand> storeBrands = storeBrandDao.getByBrandName(params.get("brandName").toString());
                Assert.notEmpty(storeBrands);
                for (StoreBrand storeBrand : storeBrands) {
                    Optional<Store> optional = stores.stream().filter(f -> f.getCode().equals(storeBrand.getStoreCode()) && f.getOwnerCode().equals(storeBrand.getClientCode())).findFirst();
                    if (optional.isPresent())
                        result.add(optional.get());
                }
            }

            //填充属性
            result.forEach(ele -> {
                Optional<Customer> optional = customers.stream().filter(f -> f.getCode().equals(ele.getOwnerCode())).findFirst();
                if (optional.isPresent())
                    ele.setOwnerName(optional.get().getName());
                List<String> brandNames = storeBrandDao.getBrandNames(ele.getOwnerCode(), ele.getCode());
                if (null != brandNames && 0 < brandNames.size())
                    ele.setBrands(String.join(",", brandNames));
            });

            PageInfo<Store> pageInfo = new PageInfo<>(result);
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e);
            return (PageResponse) ResponseBuilder.fail(e);
        }
    }

    /**
     * 参数检验
     *
     * @param store
     */
    private void AssertStore(Store store) {
        Assert.notNull(store);
        Assert.hasText(store.getCode());
        Assert.hasText(store.getName());
    }

    /**
     * 参数检验
     *
     * @param stores
     */
    private Response<List<Response>> validate(List<Store> stores) {
        List<Response> result = new ArrayList<>();

        boolean isCodeBlank = false;
        boolean isOwnerCodeBlank = false;
        boolean isNameBlank = false;
        boolean isContactManBlank = false;
        boolean isMobilePhoneNoBlank = false;

        //检查属性
        {
            if ((null != stores) && (0 < stores.size())) {
                for (Store store : stores) {
                    if (StringUtils.isBlank(store.getCode()) && !isCodeBlank) {
                        Response response = ResponseBuilder.fail("编码不能为空！");
                        result.add(response);
                        isCodeBlank = true;
                    }
                    if (StringUtils.isBlank(store.getOwnerCode()) && !isOwnerCodeBlank) {
                        Response response = ResponseBuilder.fail("客户编码不能为空！");
                        result.add(response);
                        isOwnerCodeBlank = true;
                    }
                    if (StringUtils.isBlank(store.getName()) && !isNameBlank) {
                        Response response = ResponseBuilder.fail("名称不能为空！");
                        result.add(response);
                        isNameBlank = true;
                    }
                    if (StringUtils.isBlank(store.getContactMan()) && !isContactManBlank) {
                        Response response = ResponseBuilder.fail("联系人不能为空！");
                        result.add(response);
                        isContactManBlank = true;
                    }
                    if (StringUtils.isBlank(store.getMobilePhoneNo()) && !isMobilePhoneNoBlank) {
                        Response response = ResponseBuilder.fail("手机号不能为空！");
                        result.add(response);
                        isMobilePhoneNoBlank = true;
                    }
                    //Assert.hasText(ele.getDistrictCode(), "行政区代码不能为空！");
                    //Assert.hasText(ele.getAddress(), "详细地址不能为空！");
                }
                ;
            }
        }

        // TODO: 2016/8/8 检查客户编码是否存在

        //检查stores中是否有同一客户的重复编码
        if (!isCodeBlank && !isOwnerCodeBlank) {
            {
                List<String> errorCode = new ArrayList<>();
                Map<String, List<Store>> ownerMap = stores.stream().collect(Collectors.groupingBy(Store::getOwnerCode));
                ownerMap.entrySet().forEach(ele -> {
                    List<Store> storeList = ele.getValue();
                    Map<String, List<Store>> codeMap = storeList.stream().collect(Collectors.groupingBy(Store::getCode));
                    codeMap.forEach((key, value) -> {
                        List<Store> list = codeMap.get(key);
                        if (1 < list.size())
                            errorCode.add(key);
                    });
                });
                if (null != errorCode && 0 < errorCode.size()) {
                    Response response = ResponseBuilder.fail("同一个客户中门店编码:" + String.join(",", errorCode) + "重复！");
                    result.add(response);
                }
            }
        }

        // TODO: 2016/8/8 批量插入数据的空格处理，手机号，电话号码的验证

        //检查同一客户是否已存在该门店编码(不需要的原因，客户编码和门店编码都相同则更新数据)
        /*{
            List<Store> allStores = storeDao.getAll();
            List<String> errorCode = new ArrayList<>();
            stores.forEach(ele -> {
                for (Store e : allStores) {
                    if (ele.getOwnerCode().equalsIgnoreCase(e.getOwnerCode()) && ele.getCode().equalsIgnoreCase(e.getCode())) {
                        errorCode.add(ele.getOwnerCode() + ":" + ele.getCode());
                        break;
                    }
                }
            });
            if (null != errorCode && 0 < errorCode.size())
                return Response.fail(String.join(",", errorCode) + "编码已存在（客户编码：门店编码）！");
        }*/

        //检查客户是否有该品牌
        {
            List<String> errorBrands = new ArrayList<>();
            Map<String, List<Brand>> map = brandDao.getAvailable().stream().collect(Collectors.groupingBy(e -> e.getCode().toUpperCase() + e.getCustomerCode().toUpperCase()));
            stores.forEach(ele -> {
                if (null != ele.getBrands()) {
                    String[] brands = ele.getBrands().split(",");
                    for (String brand : brands) {
                        List<Brand> list = map.get(brand.toUpperCase() + ele.getOwnerCode().toUpperCase());
                        if (null == list || 0 == list.size())
                            errorBrands.add(ele.getOwnerCode() + ":" + brand);
                    }
                }
            });
            if (null != errorBrands && 0 < errorBrands.size()) {
                Response response = ResponseBuilder.fail(String.join(",", errorBrands) + "不存在的品牌编码（客户编码：品牌编码）！");
                result.add(response);
            }
        }

        //验证不通过
        if ((null != result) && (0 < result.size())) {
            Response response = ResponseBuilder.fail("条件验证失败！");
            response.setBody(result);
            return response;
        }

        //验证通过
        return ResponseBuilder.success();
    }

    /**
     * @param store
     * @return
     */
    private List<StoreBrand> addStoreBrands(Store store) {
        List<StoreBrand> storeBrands = new ArrayList<>();
        if (null != store.getBrandCodes() && 0 < store.getBrandCodes().size()) {
            store.getBrandCodes().forEach(brandCode -> {
                StoreBrand storeBrand = new StoreBrand();
                storeBrand.setStoreBrandId(Snowflake.getInstance().next());
                storeBrand.setStoreCode(store.getCode());
                storeBrand.setBrandCode(brandCode);
                storeBrand.setClientCode(store.getOwnerCode());
                storeBrands.add(storeBrand);
            });
        }
        return storeBrands;
    }

    @Override
    public Response activate(String code) {
        try {
            boolean isSuccess = storeDao.activate(code) > 0;
            if (isSuccess)
                return ResponseBuilder.success("启用成功！");
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error(className + "activate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            boolean isSuccess = storeDao.inactivate(code) > 0;
            if (isSuccess)
                return ResponseBuilder.success("禁用成功！");
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error(className + "activate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}
