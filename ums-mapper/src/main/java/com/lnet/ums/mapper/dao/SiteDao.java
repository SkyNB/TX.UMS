package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.site.Site;

import java.util.List;
import java.util.Map;

public interface SiteDao {
    boolean exists(String code, String roleId);

    int insert(Site record);

    int update(Site record);

    int deleteById(String siteId);

    Site get(String siteId);

    Site getByCode(String code);

    List<Site> getByBranchCode(String branchCode);

    List<Site> pageList(Map<String, Object> params);

    List<Site> findByAddressBranch(String districtCode, String branchCode);

    List<Site> findAll();
}
