package com.lnet.ums.contract.api;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.Customer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface CustomerService {
    /**
     * 根据ID查询单个客户
     *
     * @param clientId
     * @return
     */
    Response<Customer> get(String clientId);

    /**
     * 根据编码查询单个客户(所有的品牌信息将会查询出来)
     *
     * @param code
     * @return
     */
    Response<Customer> getByCode(String code);

    /**
     * 根据编码查询单个客户(可用的品牌信息将会查询出来)
     *
     * @param code
     * @return
     */
    Response<Customer> getByCodeAndBrandsLimit(String code);

    /**
     * 检查客户是否存在
     *
     * @param code
     * @return
     */
    boolean exists(String code);

    /**
     * 创建客户
     *
     * @param client
     */
    Response<Customer> create(Customer client);

    /**
     * 批量创建客户
     *
     * @param customers
     * @return
     */
    Response<List<Customer>> batchCreate(List<Customer> customers);

    /**
     * 启用客户
     *
     * @param code
     */
    Response activate(String code);

    /**
     * 禁用客户
     *
     * @param code
     */
    Response inactivate(String code);

    /**
     * 更新客户信息
     *
     * @param client
     */
    Response<Customer> update(Customer client);

    /**
     * 查询所有客户
     *
     * @return
     */
    Response<List<Customer>> getAll();

    /**
     * 查询所有可用客户列表
     *
     * @return
     */
    Response<List<Customer>> getAvailable();

    /**
     * 分页
     *
     * @param startPage
     * @param pageSize
     * @param params
     * @return
     */
    PageResponse<Customer> pageList(int startPage, int pageSize, Map<String, Object> params);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean batchInactivate(List<String> ids);

    /**
     * 同一业务组的客户信息
     *
     * @param bizGroupCode
     * @return
     */
    Response<List<Customer>> getByBizGroupCode(String bizGroupCode);

    /**
     * 查询当前分公司所有可用的客户
     *
     * @param branchCode
     * @return
     */
    ListResponse<Customer> findCustomerForBranch(String branchCode);

    Object importCustomer(InputStream inputStream, String userId);
}
