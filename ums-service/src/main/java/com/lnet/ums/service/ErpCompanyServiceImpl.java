package com.lnet.ums.service;

import com.lnet.model.ums.ErpCompany;
import com.lnet.ums.contract.api.ErpCompanyService;
import com.lnet.ums.mapper.dao.mappers.ErpCompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wanJ on 2017/2/15.
 */
@Service
@Transactional
@Slf4j
public class ErpCompanyServiceImpl implements ErpCompanyService {

    @Resource
    ErpCompanyMapper erpCompanyMapper;

    @Override
    public List<ErpCompany> selectErpCompanyList(Map map) {
        return erpCompanyMapper.selectCompanyList(map);
    }

    /**
     * 根据companyId逻辑删除公司
     * @param validate
     * @param companyId
     * @return
     */
    @Override
    public boolean logicDeleteCompany(int validate, String companyId) {
        ErpCompany erpCompany = new ErpCompany();
        erpCompany.setCompanyid(companyId);
        erpCompany.setValidate(validate);
        int i = erpCompanyMapper.logicDeleteCompany(erpCompany);
        if(i!=1){
            return false;
        }
        return true;
    }

}
