package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.GoodsIdentificationCode;
import com.lnet.ums.mapper.dao.GoodsIdentificationCodeDao;
import com.lnet.ums.mapper.dao.mappers.GoodsIdentificationCodeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class GoodsIdentificationCodeDaoImpl implements GoodsIdentificationCodeDao {
    @Resource
    GoodsIdentificationCodeMapper mapper;
    @Override
    public int batchInsert(List<GoodsIdentificationCode> codes) {
        return mapper.batchInsert(codes);
    }

    @Override
    public int deleteByArchivesId(String archivesId) {
        return mapper.deleteByArchivesId(archivesId);
    }

    @Override
    public int batchDelByArchivesIds(List<String> archivesIds) {
        return mapper.batchDelByArchivesIds(archivesIds);
    }

    @Override
    public List<GoodsIdentificationCode> getByArchivesId(String archivesId) {
        return mapper.getByArchivesId(archivesId);
    }
}
