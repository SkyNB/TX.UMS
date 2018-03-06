package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.Store;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StoreMapper {
    boolean exists(@Param("ownerCode") String customerCode, @Param("code") String code, @Param("storeId") String storeId);

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
