package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.site.Site;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SiteMapper {
    boolean exists(@Param("code") String code, @Param("siteId") String roleId);

    int insert(Site record);

    int update(Site record);

    int deleteById(String siteId);

    Site get(String siteId);

    Site getByCode(String code);

    List<Site> getByBranchCode(String branchCode);

    List<Site> pageList(Map<String, Object> params);

    List<Site> findByAddressBranch(@Param("districtCode") String districtCode, @Param("branchCode") String branchCode);

    List<Site> findAll();
}
