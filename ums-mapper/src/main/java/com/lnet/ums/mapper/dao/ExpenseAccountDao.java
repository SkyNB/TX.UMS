package com.lnet.ums.mapper.dao;




import com.lnet.model.ums.expense.ExpenseAccount;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/21.
 */

public interface ExpenseAccountDao {
    int create(ExpenseAccount expenseAccount);

    int update(ExpenseAccount expenseAccount);

    int delete(String code);

    int deleteBySuperCode(String code);

    boolean exists(String expenseAccountId, String code);

    ExpenseAccount get(String expenseAccountId);

    ExpenseAccount getByCode(String code);

    List<ExpenseAccount> pageList(Map<String, Object> params);

    List<ExpenseAccount> findBySuperCode(String code);

    List<ExpenseAccount> findAll();
}
