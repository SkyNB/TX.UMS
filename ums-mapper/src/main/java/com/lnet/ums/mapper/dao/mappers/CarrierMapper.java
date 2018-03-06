package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.carrier.carrierEntity.Carrier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CarrierMapper {
    Boolean exists(@Param("code") String code, @Param("carrierId") String carrierId);

    int insert(Carrier carrier);

    int batchInsert(List<Carrier> carrier);

    int update(Carrier carrier);

    List<Carrier> getAll();

    Carrier get(String carrierId);

    Carrier getByCode(String code);

    List<Carrier> getByBranchCode(String branchCode);

    List<Carrier> getByBranchCodeAndAvailable(String branchCode);

    int inactivate(String code);

    int activate(String code);

    List<Carrier> search(Map<String, Object> params);
}
