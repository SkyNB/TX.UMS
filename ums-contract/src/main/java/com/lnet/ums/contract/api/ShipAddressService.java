package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.ShipAddress;

import java.util.List;
import java.util.Map;

public interface ShipAddressService {

    /**
     * 创建发运地址
     *
     * @param shipAddress
     * @return
     */
    Response<String> create(ShipAddress shipAddress);

    /**
     * 修改发运地址
     *
     * @param shipAddress
     * @return
     */
    Response<String> update(ShipAddress shipAddress);

    /**
     * 所有的发运地址
     *
     * @return
     */
    Response<List<ShipAddress>> getAll();

    /**
     * 根据ID获得发运地址的信息
     *
     * @param shipAddressId
     * @return
     */
    Response<ShipAddress> get(String shipAddressId);

    /**
     * 根据编码获得发运地址的信息
     *
     * @param code
     * @return
     */
    Response<ShipAddress> getByCode(String code);

    /**
     * 承运商的所有发运地址
     *
     * @param carrierCode
     * @return
     */
    Response<List<ShipAddress>> getCarrier(String carrierCode);

    /**
     * 获得承运商可用的发运地址
     *
     * @param carrierCode
     * @return
     */
    Response<List<ShipAddress>> getCarrierAvailable(String carrierCode);

    /**
     * 禁用发运地址
     *
     * @param code
     * @return
     */
    Response inactivate(String code);

    /**
     * 启用发运地址
     *
     * @param code
     * @return
     */
    Response activate(String code);

    PageResponse<ShipAddress> pageList(int pageNo, int pageSize, Map<String, Object> params);
}
