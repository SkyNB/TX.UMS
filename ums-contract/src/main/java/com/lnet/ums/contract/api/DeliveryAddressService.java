package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.DeliveryAddress;

import java.util.List;
import java.util.Map;

public interface DeliveryAddressService {
    /**
     * 创建送货地址
     *
     * @param deliveryAddress
     * @return
     */
    Response create(DeliveryAddress deliveryAddress);

    /**
     * 修改送货地址
     *
     * @param deliveryAddress
     * @return
     */
    Response update(DeliveryAddress deliveryAddress);

    /**
     * 获得所有的送货地址
     *
     * @return
     */
    Response<List<DeliveryAddress>> getAll();

    /**
     * 获得所有可用的送货地址
     *
     * @return
     */
    Response<List<DeliveryAddress>> getAvailable();

    /**
     * 根据ID获得送货地址信息
     *
     * @param deliveryAddressId
     * @return
     */
    Response<DeliveryAddress> get(String deliveryAddressId);

    /**
     * 根据客户编码查询所有可用的送货地址
     *
     * @param ownerCode
     * @return
     */
    Response<List<DeliveryAddress>> getByOwnerCode(String ownerCode);

    /**
     *禁用送货地址信息
     *
     * @param deliveryAddressId
     * @return
     */
    Response inactivate(String deliveryAddressId);

    /**
     * 启用送货地址
     *
     * @param deliveryAddressId
     * @return
     */
    Response activate(String deliveryAddressId);

    PageResponse<DeliveryAddress> pageList(int pageNo, int pageSize, Map<String, Object> params);

    Response<List<DeliveryAddress>> findByOwnerCode(String ownerCode);
}
