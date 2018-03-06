package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.GoodsIdentificationCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsIdentificationCodeMapper {
    int batchInsert(List<GoodsIdentificationCode> codes);

    int deleteByArchivesId(String archivesId);

    int batchDelByArchivesIds(List<String> archivesIds);

    List<GoodsIdentificationCode> getByArchivesId(String archivesId);
}