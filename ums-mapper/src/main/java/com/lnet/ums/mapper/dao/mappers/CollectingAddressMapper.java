package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.CollectingAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CollectingAddressMapper {
    boolean exists(@Param("code") String code, @Param("ownerCode") String ownerCode, @Param("collectingAddressId") String collectingAddressId);

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
