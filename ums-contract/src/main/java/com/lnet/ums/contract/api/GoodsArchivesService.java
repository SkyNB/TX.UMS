package com.lnet.ums.contract.api;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.GoodsArchives;
import com.lnet.model.ums.customer.customerDto.GoodsArchivesDto;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface GoodsArchivesService {
    /**
     * 根据ID查询商品档案
     *
     * @param goodsArchiveId
     * @return
     */
    Response<GoodsArchives> get(String goodsArchiveId);

    /**
     * 根据编码查询商品档案
     *
     * @param branchCode
     * @param customerCode
     * @param code
     * @return
     */
    Response<GoodsArchives> getByCode(String branchCode, String customerCode, String code);

    /**
     * 创建商品档案
     *
     * @param goodsArchive
     * @return
     */
    Response<GoodsArchives> create(GoodsArchives goodsArchive);

    /**
     * 批量创建商品档案
     *
     * @param goodsArchivesList
     * @return
     */
    ListResponse<GoodsArchives> batchCreate(List<GoodsArchives> goodsArchivesList);

    /**
     * 更新商品档案
     *
     * @param goodsArchive
     * @return
     */
    Response<GoodsArchives> update(GoodsArchives goodsArchive);

    /**
     * 分页查询
     *
     * @param startPage
     * @param pageSize
     * @param params
     * @return
     */
    PageResponse<GoodsArchivesDto> pageList(int startPage, int pageSize, Map<String, Object> params);

    /**
     * 批量删除
     *
     * @param goodsArchiveIds
     * @return
     */
    Response batchDelete(List<String> goodsArchiveIds);

    /**
     * 一个项目的所有商品档案
     *
     * @param branchCode
     * @param customerCode
     * @return
     */
    ListResponse<GoodsArchives> getByBranchCodeAndCustomerCode(String branchCode, String customerCode);

    /**
     * 根据识别码查找商品档案
     *
     * @param branchCode
     * @param identificationCode
     * @return
     */
    Response<GoodsArchives> getByIdentificationCode(String branchCode, String identificationCode);

    Object importGoodsArchives(InputStream inputStream, String currentBranchCode);
}
