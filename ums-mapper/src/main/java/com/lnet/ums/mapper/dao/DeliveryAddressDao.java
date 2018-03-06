package com.lnet.ums.mapper.dao;




import com.lnet.model.ums.customer.customerEntity.DeliveryAddress;

import java.util.List;
import java.util.Map;

public interface DeliveryAddressDao {
    int insert(DeliveryAddress deliveryAddress);

    int update(DeliveryAddress deliveryAddress);

    List<DeliveryAddress> getAll();

    List<DeliveryAddress> getAvailable();

    DeliveryAddress get(String deliveryAddressId);

    List<DeliveryAddress> getByOwnerCode(String ownerCode);

    int activate(String deliveryAddressId);

    int inactivate(String deliveryAddressId);

    List<DeliveryAddress> pageList(Map<String, Object> params);
}
