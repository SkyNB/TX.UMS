package com.lnet.ums.mapper.dao.mappers;


import com.lnet.model.ums.customer.customerEntity.GoodsArchives;
import com.lnet.model.ums.customer.customerDto.GoodsArchivesDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsArchivesMapper {
    int delete(String archivesId);

    int insert(GoodsArchives goodsArchives);

    int batchInsert(List<GoodsArchives> goodsArchives);

    GoodsArchives get(String archivesId);

    GoodsArchives getByCode(@Param("branchCode") String branchCode, @Param("customerCode") String customerCode, @Param("code") String code);

    int update(GoodsArchives goodsArchives);

    Boolean exists(@Param("branchCode") String branchCode, @Param("customerCode") String customerCode, @Param("code") String code, @Param("archivesId") String archivesId);

    Boolean identificationCodeExists(@Param("branchCode") String branchCode, @Param("identificationCode") String identificationCode, @Param("archivesId") String archivesId);

    List<GoodsArchives> findByBranchCodeAndCustomerCode(@Param("branchCode") String branchCode, @Param("customerCode") String customerCode);

    List<GoodsArchives> findByBranchCode(String branchCode);

    List<GoodsArchivesDto> pageList(Map<String, Object> params);

    int batchDelete(List<String> archivesIds);

    GoodsArchives getByIdentificationCode(@Param("branchCode") String branchCode, @Param("identificationCode") String identificationCode);
}