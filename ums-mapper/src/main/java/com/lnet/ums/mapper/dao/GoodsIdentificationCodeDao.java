package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.GoodsIdentificationCode;

import java.util.List;

public interface GoodsIdentificationCodeDao {
    int batchInsert(List<GoodsIdentificationCode> codes);

    int deleteByArchivesId(String archivesId);

    int batchDelByArchivesIds(List<String> archivesIds);

    List<GoodsIdentificationCode> getByArchivesId(String archivesId);
}