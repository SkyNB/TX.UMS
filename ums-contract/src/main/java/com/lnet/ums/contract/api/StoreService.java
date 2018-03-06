package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.Store;

import java.util.List;
import java.util.Map;

/**
 * 门店服务类，包含门店和仓库
 */
public interface StoreService {

    // QueryResult query(Query params);

    /**
     * 查询单个门店
     *
     * @param storeId
     * @return
     */
    Response<Store> get(String storeId);

    /**
     * 根据编码查询单个门店
     *
     * @param code
     * @return
     */
    Response<Store> getByCode(String code);


    /**
     * 创建门店
     *
     * @param store
     */
    Response<Store> create(Store store);

    /**
     * 更新门店信息
     *
     * @param store
     */
    Response<Store> update(Store store);

    /**
     * 删除门店
     *
     * @param storeIds
     */
    boolean batchDelete(List<String> storeIds);


    /**
     * 批量创建门店
     * 只有当所有数据符合条件时才进行插入操作
     *
     * @param stores
     */
    Response<List<Response>> batchInsert(List<Store> stores);

    /**
     * 根据客户编码查询当前客户可用的门店列表
     *
     * @param clientCode
     * @return
     */
    Response<List<Store>> getAvailable(String clientCode);

    /**
     * 根据客户编码，品牌查询当前客户下品牌可用的门店列表
     *
     * @param clientCode
     * @param brand
     * @return
     */
    Response<List<Store>> getAvailable(String clientCode, String brand);

    /**
     * 查询当前分支机构下所有项目可用的门店列表
     *
     * @param branchCode
     * @return
     */
    Response<List<Store>> getBranchAvailable(String branchCode);

    /**
     * 查询当前分支机构下，当前客户可用的门店列表
     *
     * @param branchCode
     * @param clientCode
     * @return
     */
    Response<List<Store>> getBranchAvailable(String branchCode, String clientCode);

    /**
     * 根据客户获取门店数据
     *
     * @param ownerCode
     * @return
     */
    Response<List<Store>> getByOwnerCode(String ownerCode);

    /**
     * 分页
     *
     * @param startPage
     * @param pageSize
     * @param params
     * @return
     */
    PageResponse<Store> pageList(int startPage, int pageSize, Map<String, Object> params);

    /**
     * 启用门店
     *
     * @param code
     */
    Response activate(String code);

    /**
     * 禁用门店
     *
     * @param code
     */
    Response inactivate(String code);
}
