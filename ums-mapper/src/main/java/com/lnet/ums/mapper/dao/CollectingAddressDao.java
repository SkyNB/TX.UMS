package com.lnet.ums.mapper.dao;


import com.lnet.model.ums.customer.customerEntity.CollectingAddress;

import java.util.List;
import java.util.Map;

public interface CollectingAddressDao {
    boolean exists(String code, String ownerCode, String collectingAddressId);

    int insert(CollectingAddress collectingAddress);

    int batchInsert(List<CollectingAddress> collectingAddresses);

    int update(CollectingAddress collectingAddress);

    List<CollectingAddress> getAll();

    List<CollectingAddress> getAvailable();

    CollectingAddress get(String collectingAddressId);

    CollectingAddress getByCode(String code);

    List<CollectingAddress> getByOwnerCode(String ownerCode);

    int activate(String code);

    int inactivate(String code);

    List<CollectingAddress> pageList(Map<String, Object> params);
}
