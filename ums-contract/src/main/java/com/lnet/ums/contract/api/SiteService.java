package com.lnet.ums.contract.api;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.site.Site;
import java.util.List;
import java.util.Map;

public interface SiteService {
    /**
     * 检查站点是否存在
     *
     * @param code
     * @return
     */
    boolean exists(String code);

    /**
     * 创建站点
     *
     * @param site
     * @throws Exception
     */
    Response<Site> create(Site site);

    /**
     * 更新站点
     *
     * @param site
     * @throws Exception
     */
    Response<Site> update(Site site);

    /**
     * 删除站点
     *
     * @param siteId
     * @throws Exception
     */
    Response delete(String siteId);

    /**
     * 根据ID查询单个站点
     *
     * @param siteId
     * @return
     */
    Response<Site> get(String siteId);

    /**
     * 根据code查询单个站点
     *
     * @param code
     * @return
     */
    Response<Site> getByCode(String code);

    /**
     * 根据分公司查询 站点
     *
     * @param branchCode
     * @return
     */
    Response<List<Site>> getByBranchCode(String branchCode);

    /**
     * 分页查询
     *
     * @param pageNumber 页数
     * @param pageSize   每页数据量
     * @param params     过滤条件
     * @return
     */
    PageResponse<Site> pageList(int pageNumber, int pageSize, Map<String, Object> params);

    /**
     * 根据地址和分支机构查询出详细地址
     *
     * @param districtCode
     * @param branchCode
     * @return
     */
    ListResponse<Site> findByAddressBranch(String districtCode, String branchCode);

    /**
     * 查询所有站点
     *
     * @return
     */
    ListResponse<Site> findAll();
}
