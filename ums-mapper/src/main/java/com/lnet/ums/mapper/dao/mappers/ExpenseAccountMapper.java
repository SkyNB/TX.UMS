package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.expense.ExpenseAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/21.
 */
@Repository
public interface ExpenseAccountMapper {
    int create(ExpenseAccount expenseAccount);

    int update(ExpenseAccount expenseAccount);

    int delete(String code);

    int deleteBySuperCode(String code);

    boolean exists(@Param("expenseAccountId") String expenseAccountId, @Param("code") String code);

    ExpenseAccount get(String expenseAccountId);

    ExpenseAccount getByCode(String code);

    List<ExpenseAccount> pageList(Map<String, Object> params);

    List<ExpenseAccount> findBySuperCode(String code);

    List<ExpenseAccount> findAll();
}
