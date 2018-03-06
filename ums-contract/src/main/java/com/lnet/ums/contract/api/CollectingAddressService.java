package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.CollectingAddress;

import java.util.List;
import java.util.Map;

public interface CollectingAddressService {
    boolean exists(String code, String ownerCode, String collectingAddressId) throws Exception;

    /**
     * 添加提货地址
     *
     * @param collectingAddress
     * @return
     */
    Response create(CollectingAddress collectingAddress);

    /**
     * 批量添加提货地址
     *
     * @param collectingAddresses
     * @return
     */
    Response batchInsert(List<CollectingAddress> collectingAddresses);

    /**
     * 修改提货地址
     *
     * @param collectingAddress
     * @return
     */
    Response update(CollectingAddress collectingAddress);

    /**
     * 获取所有的提货地址
     *
     * @return
     */
    Response<List<CollectingAddress>> getAll();

    /**
     * 获取所有用的提货地址
     *
     * @return
     */
    Response<List<CollectingAddress>> getAvailable();

    /**
     * 根据ID获取提货地址
     *
     * @param collectingAddressId
     * @return
     */
    Response<CollectingAddress> get(String collectingAddressId);

    /**
     * 根据code获取提货地址
     *
     * @param code
     * @return
     */
    Response<CollectingAddress> getByCode(String code);

    /**
     * 根据客户获取所有可用的提货地址信息
     *
     * @param ownerCode
     * @return
     */
    Response<List<CollectingAddress>> getByOwnerCode(String ownerCode);

    /**
     * 启用提货地址
     *
     * @param code
     * @return
     */
    Response inactivate(String code);

    /**
     * 禁用提货地址
     *
     * @param code
     * @return
     */
    Response activate(String code);

    PageResponse<CollectingAddress> pageList(int pageNo, int pageSize, Map<String, Object> params);
}
