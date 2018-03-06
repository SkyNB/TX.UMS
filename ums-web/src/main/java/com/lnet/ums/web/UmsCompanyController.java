package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.PageResponse;
import com.lnet.model.ums.ErpCompany;
import com.lnet.model.ums.ErpUser;
import com.lnet.ums.contract.api.ErpCompanyService;
import com.lnet.ums.contract.api.UserContract;
import com.lnet.ums.web.utils.ConstantNuoch;
import com.lnet.ums.web.utils.JsonUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wanJ on 2017/2/15.
 * 公司信息
 */
@RequestMapping(value = "company")
@Controller
public class UmsCompanyController {

    @Resource
    UserContract userContract;

    @Resource
    ErpCompanyService erpCompanyService;

    @RequestMapping(value = "selectCompany",method = RequestMethod.GET)
    public String selectCompany(ModelMap map, Map params) {
        //根据登录用户获取所能看的公司信息
        List<ErpCompany> list = erpCompanyService.selectErpCompanyList(params);
        if(list!=null&&list.size()>0){
            map.put("ErpCompany", JsonUtils.toJson(list));
        }else{
            return "common/error";
        }
        return "company/index";
    }

    @RequestMapping(value = "selectCompanyJson",method = RequestMethod.GET)
    public @ResponseBody
    List<ErpUser> selectCompanyJson(ModelMap map, String userId) {
        //根据登录用户userId获取所能看的用户信息
        List<ErpUser> list = userContract.selectUserCustom(ConstantNuoch.CONSTANT_VALIDATE_601,ConstantNuoch.CONSTANT_VALIDATE_602);
        return list;
    }

    @RequestMapping(value = "/selectCompanyList", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse<ErpUser> selectCompanyJsonT(@RequestBody KendoGridRequest params) {
        Map<String,Object> userMap = null;
        if(null != params.getParams()){
            userMap = params.getParams();
        } else {
            userMap = new HashedMap();
        }
        userMap.put("validateNormal",ConstantNuoch.CONSTANT_VALIDATE_601);
        userMap.put("validateDisable",ConstantNuoch.CONSTANT_VALIDATE_602);

        return userContract.selectUserCustomList(params.getPage(),params.getPageSize(),userMap);
    }

    /**
     * 逻辑删除公司
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/deleteCompany",method = RequestMethod.POST)
    public @ResponseBody String deleteCompany(ModelMap map,/*@RequestParam(value = "userId", required = true) */String companyId){
        boolean stat = erpCompanyService.logicDeleteCompany(ConstantNuoch.CONSTANT_VALIDATE_603,companyId);
        if(!stat){
            return "error";
        }
        return "success";
    }

    /**
     * 停用公司
     * @param map
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/disableCompany",method = RequestMethod.POST)
    public @ResponseBody String disableCompany(ModelMap map,String companyId){
        boolean stat = erpCompanyService.logicDeleteCompany(ConstantNuoch.CONSTANT_VALIDATE_602,companyId);
        if(!stat){
            return "error";
        }
        return "success";
    }

    /**
     * 启用公司
     * @param map
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/startCompany",method = RequestMethod.POST)
    public @ResponseBody String startCompany(ModelMap map,String companyId){
        boolean stat = erpCompanyService.logicDeleteCompany(ConstantNuoch.CONSTANT_VALIDATE_601,companyId);
        if(!stat){
            return "error";
        }
        return "success";
    }

}
