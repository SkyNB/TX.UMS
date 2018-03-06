package com.lnet.ums.mapper.dao;




import com.lnet.model.ums.customer.customerEntity.GoodsArchives;
import com.lnet.model.ums.customer.customerDto.GoodsArchivesDto;

import java.util.List;
import java.util.Map;

public interface GoodsArchivesDao {
    int delete(String archivesId);

    int insert(GoodsArchives goodsArchives);

    int batchInsert(List<GoodsArchives> goodsArchives);

    GoodsArchives get(String archivesId);

    GoodsArchives getByCode(String branchCode, String customerCode, String code);

    int update(GoodsArchives goodsArchives);

    Boolean exists(String branchCode, String customerCode, String code, String archivesId);

    Boolean identificationCodeExists(String branchCode, String identificationCode, String archivesId);

    List<GoodsArchives> findByBranchCodeAndCustomerCode(String branchCode, String customerCode);

    List<GoodsArchives> findByBranchCode(String branchCode);

    List<GoodsArchivesDto> pageList(Map<String, Object> params);

    int batchDelete(List<String> archivesIds);

    GoodsArchives getByIdentificationCode(String branchCode, String identificationCode);
}