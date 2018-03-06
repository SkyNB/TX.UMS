package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.ErpCompany;
import com.lnet.model.ums.ErpCompanyWithBLOBs;

import java.util.List;
import java.util.Map;

/**
 * 公司信息Mapper
 */
public interface ErpCompanyMapper {
    int deleteByPrimaryKey(String companyid);

    int insert(ErpCompanyWithBLOBs record);

    int insertSelective(ErpCompanyWithBLOBs record);

    ErpCompanyWithBLOBs selectByPrimaryKey(String companyid);

    int updateByPrimaryKeySelective(ErpCompanyWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ErpCompanyWithBLOBs record);

    int updateByPrimaryKey(ErpCompany record);

    /**
     * 分页查找公司信息
     * @param map
     * @return
     */
    List<ErpCompany> selectCompanyList(Map map);

    /**
     * 根据userId逻辑删除用户
     * @param record
     * @return
     */
    int logicDeleteCompany(ErpCompany record);

}