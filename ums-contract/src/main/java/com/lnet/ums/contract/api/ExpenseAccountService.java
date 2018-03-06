package com.lnet.ums.contract.api;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.expense.ExpenseAccount;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface ExpenseAccountService {
    /**
     * 删除 包含下级
     * @param code
     * @return
     */
    Response deleteByCode(String code);

    Response<ExpenseAccount> create(ExpenseAccount record);

    /**
     * 查询下级费用科目
     * @param superiorCode
     * @return
     */
    ListResponse<ExpenseAccount> findChildren(String superiorCode);
    ListResponse<ExpenseAccount> findAll();

    Response<ExpenseAccount> get(String expenseAccountId);
    Response<ExpenseAccount> getByCode(String code);

    Response<ExpenseAccount> update(ExpenseAccount record);

    PageResponse<ExpenseAccount> pageList(int pageNumber, int pageSize, Map<String, Object> params);
}
