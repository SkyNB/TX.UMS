package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.ShipAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShipAddressMapper {
    boolean exists(@Param("code") String code, @Param("shipAddressId") String shipAddressId);

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
