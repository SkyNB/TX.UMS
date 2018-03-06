package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.Customer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerMapper {
    boolean exists(@Param("code") String code, @Param("customerId") String rowId);

    Customer get(String clientId);

    Customer getByCode(String code);

    int insert(Customer client);

    int batchInsert(List<Customer> clients);

    int update(Customer client);

    int activate(String code);

    int inactivate(String code);

    List<Customer> getAvailable();

    List<Customer> getAll();

    List<Customer> getByCodes(List<String> codes);

    List<Customer> pageList(Map<String, Object> params);

    int batchInactivate(List<String> ids);

    List<Customer> getByBizGroupCode(String bizGroupCode);
}
