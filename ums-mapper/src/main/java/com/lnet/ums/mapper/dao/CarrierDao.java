package com.lnet.ums.mapper.dao;




import com.lnet.model.ums.carrier.carrierEntity.Carrier;

import java.util.List;
import java.util.Map;

public interface CarrierDao {
    Boolean exists(String code, String carrierId);

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
