package com.lnet.ums.mapper.dao;


import com.lnet.model.ums.customer.customerEntity.ShipAddress;

import java.util.List;
import java.util.Map;

public interface ShipAddressDao {
    boolean exists(String code, String shipAddressId);

    int insert(ShipAddress shipAddress);

    int update(ShipAddress shipAddress);

    List<ShipAddress> getAll();

    ShipAddress get(String shipAddressId);

    ShipAddress getByCode(String code);

    List<ShipAddress> getByCarrierCode(String carrierCode);

    List<ShipAddress> getByCarrierCodeAndAvailable(String carrierCode);

    int inactivate(String code);

    int activate(String code);

    List<ShipAddress> pageList(Map<String, Object> params);
}
