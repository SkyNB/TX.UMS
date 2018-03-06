package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.ListResponse;
import com.lnet.model.ums.expense.ExpenseAccount;
import com.lnet.ums.contract.api.ExpenseAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/9/23.
 */
@Controller
@RequestMapping("expenseAccount")
public class ExpenseAccountController {

    @Resource
    ExpenseAccountService expenseAccountService;
    @RequestMapping("")
    public String index(){
        return "expenseAccount/index";
    }
    @PostMapping("search")
    public @ResponseBody Object search(@RequestBody KendoGridRequest request){
        return expenseAccountService.pageList(request.getPage(),request.getPageSize(),request.getParams());
    }
    @RequestMapping("create")
    public String create(){
        return "expenseAccount/create";
    }
    @PostMapping("create")
    public @ResponseBody Object create(@RequestBody ExpenseAccount expenseAccount){
        return expenseAccountService.create(expenseAccount);
    }
    @PostMapping("delete/{code}")
    public @ResponseBody Object create(@PathVariable String code){
        return expenseAccountService.deleteByCode(code);
    }

    @RequestMapping("findChildren/{code}")
    public @ResponseBody
    ListResponse<ExpenseAccount> findChildren(@PathVariable String code){
        return expenseAccountService.findChildren(code);
    }

}
