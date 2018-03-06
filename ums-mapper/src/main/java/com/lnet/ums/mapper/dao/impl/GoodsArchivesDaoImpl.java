package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.GoodsArchives;
import com.lnet.model.ums.customer.customerDto.GoodsArchivesDto;
import com.lnet.ums.mapper.dao.GoodsArchivesDao;
import com.lnet.ums.mapper.dao.mappers.GoodsArchivesMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class GoodsArchivesDaoImpl implements GoodsArchivesDao {

    @Resource
    GoodsArchivesMapper mapper;
    @Override
    public int delete(String archivesId) {
        return mapper.delete(archivesId);
    }

    @Override
    public int insert(GoodsArchives goodsArchives) {
        return mapper.insert(goodsArchives);
    }

    @Override
    public int batchInsert(List<GoodsArchives> goodsArchives) {
        return mapper.batchInsert(goodsArchives);
    }

    @Override
    public GoodsArchives get(String archivesId) {
        return mapper.get(archivesId);
    }

    @Override
    public GoodsArchives getByCode(String branchCode, String customerCode, String code) {
        return mapper.getByCode(branchCode,customerCode,code);
    }

    @Override
    public int update(GoodsArchives goodsArchives) {
        return mapper.update(goodsArchives);
    }

    @Override
    public Boolean exists(String branchCode, String customerCode, String code, String archivesId) {
        return mapper.exists(branchCode,customerCode,code,archivesId);
    }

    @Override
    public Boolean identificationCodeExists(String branchCode, String identificationCode, String archivesId) {
        return mapper.identificationCodeExists(branchCode,identificationCode,archivesId);
    }

    @Override
    public List<GoodsArchives> findByBranchCodeAndCustomerCode(String branchCode, String customerCode) {
        return mapper.findByBranchCodeAndCustomerCode(branchCode,customerCode);
    }

    @Override
    public List<GoodsArchives> findByBranchCode(String branchCode) {
        return mapper.findByBranchCode(branchCode);
    }

    @Override
    public List<GoodsArchivesDto> pageList(Map<String, Object> params) {
        return mapper.pageList(params);
    }

    @Override
    public int batchDelete(List<String> archivesIds) {
        return mapper.batchDelete(archivesIds);
    }

    @Override
    public GoodsArchives getByIdentificationCode(String branchCode, String identificationCode) {
        return mapper.getByIdentificationCode(branchCode,identificationCode);
    }
}
