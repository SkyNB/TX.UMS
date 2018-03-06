package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    boolean exists(String code, String rowId);

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
