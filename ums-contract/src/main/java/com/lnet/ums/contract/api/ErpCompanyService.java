package com.lnet.ums.contract.api;

import com.lnet.model.ums.ErpCompany;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/15.
 */
public interface ErpCompanyService {

    /**
     * 根据用户id查找所拥有的资源菜单
     * @param map
     * @return
     */
    List<ErpCompany> selectErpCompanyList(Map map);

    /**
     * 根据companyId逻辑删除用户
     * @param validate
     * @param companyId
     * @return
     */
    boolean logicDeleteCompany(int validate,String companyId);

}
