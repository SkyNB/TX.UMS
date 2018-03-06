package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.model.ums.expense.ExpenseAccount;
import com.lnet.ums.contract.api.ExpenseAccountService;
import com.lnet.ums.mapper.dao.ExpenseAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/21.
 */
@Service
@Transactional
@Slf4j
public class ExpenseAccountServiceImpl implements ExpenseAccountService {

    @Resource
    ExpenseAccountDao expenseAccountDao;

    @Override
    public Response deleteByCode(String code) {
        try {
            Assert.hasText(code);
            Assert.isTrue(expenseAccountDao.delete(code)>0,"删除失败");
            expenseAccountDao.deleteBySuperCode(code);
            return ResponseBuilder.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage(),e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<ExpenseAccount> create(ExpenseAccount record) {
        try {
            Assert.notNull(record);
            Assert.hasText(record.getCode());
            Assert.hasText(record.getName());
            record.setExpenseAccountId(Snowflake.getInstance().next());
            if(expenseAccountDao.exists(null,record.getCode())){
                ResponseBuilder.fail("编码已存在！");
            }
            Assert.isTrue(expenseAccountDao.create(record)>0,"添加失败");
            return ResponseBuilder.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage(),e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public ListResponse<ExpenseAccount> findChildren(String superiorCode) {

        try {
            Assert.hasText(superiorCode);
            return ResponseBuilder.list(expenseAccountDao.findBySuperCode(superiorCode));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseBuilder.listFail(e);
        }
    }

    @Override
    public ListResponse<ExpenseAccount> findAll() {
        try {
            return ResponseBuilder.list(expenseAccountDao.findAll());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseBuilder.listFail(e);
        }
    }

    @Override
    public Response<ExpenseAccount> get(String expenseAccountId) {
        try {
            Assert.hasText(expenseAccountId);
            return ResponseBuilder.success(expenseAccountDao.get(expenseAccountId));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseBuilder.fail(e);
        }
    }
    @Override
    public Response<ExpenseAccount> getByCode(String code) {
        try {
            Assert.hasText(code);
            return ResponseBuilder.success(expenseAccountDao.getByCode(code));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<ExpenseAccount> update(ExpenseAccount record) {
        try {
            Assert.notNull(record);
            Assert.hasText(record.getExpenseAccountId());
            Assert.hasText(record.getCode());
            Assert.hasText(record.getName());
            if(expenseAccountDao.exists(record.getExpenseAccountId(),record.getCode())){
                ResponseBuilder.fail("编码已存在！");
            }
            Assert.isTrue(expenseAccountDao.update(record)>0,"修改失败");
            return ResponseBuilder.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage(),e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public PageResponse<ExpenseAccount> pageList(int pageNumber, int pageSize, Map<String, Object> params) {
        try{
            PageHelper.startPage(pageNumber,pageSize);
            PageInfo<ExpenseAccount> pageInfo = new PageInfo<>(expenseAccountDao.pageList(params));
            return ResponseBuilder.page(pageInfo.getList(),pageNumber,pageSize,pageInfo.getTotal());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return ResponseBuilder.pageFail(e);
        }
    }
}
