package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.Store;

import java.util.List;
import java.util.Map;

public interface StoreDao {
    boolean exists(String customerCode, String code, String storeId);

    int insert(Store store);

    int update(Store store);

    int deleteById(String storeId);

    int batchInsert(List<Store> stores);

    int batchDelete(List<String> storeIds);

    Store get(String storeId);

    Store getByCode(String code);

    List<Store> getAvailable();

    List<Store> getBranchAvailable(String branchCode);

    List<Store> getAll();

    List<Store> getByOwnerCode(String ownerCode);

    List<Store> pageList(Map<String, Object> params);

    int activate(String code);

    int inactivate(String code);
}
