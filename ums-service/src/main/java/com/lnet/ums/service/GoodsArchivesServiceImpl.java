package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.GoodsArchivesService;
import com.lnet.model.ums.customer.customerEntity.GoodsArchives;
import com.lnet.model.ums.customer.customerDto.GoodsArchivesDto;
import com.lnet.model.ums.customer.customerEntity.GoodsIdentificationCode;
import com.lnet.ums.mapper.dao.GoodsArchivesDao;
import com.lnet.ums.mapper.dao.GoodsIdentificationCodeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Transactional
@Service
@Slf4j
public class GoodsArchivesServiceImpl implements GoodsArchivesService {

    private final String className = this.getClass().getSimpleName() + ".";

    @Autowired
    private GoodsArchivesDao goodsArchivesDao;
    @Autowired
    private GoodsIdentificationCodeDao goodsIdentificationCodeDao;

    @Override
    public Response<GoodsArchives> get(String archivesId) {
        try {
            Assert.hasText(archivesId);
            GoodsArchives goodsArchives = goodsArchivesDao.get(archivesId);
            if (goodsArchives != null) {
                List<GoodsIdentificationCode> codes = goodsIdentificationCodeDao.getByArchivesId(archivesId);
                goodsArchives.setIdentificationCodes(codes);
                return ResponseBuilder.success(goodsArchives);
            } else {
                return ResponseBuilder.fail("商品档案不存在！");
            }
        } catch (Exception e) {
            log.error(className + "get", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<GoodsArchives> getByCode(String branchCode, String customerCode, String code) {
        try {
            Assert.notNull(branchCode);
            Assert.notNull(customerCode);
            Assert.hasText(code);
            GoodsArchives goodsArchives = goodsArchivesDao.getByCode(branchCode, customerCode, code);
            if (goodsArchives != null) {
                List<GoodsIdentificationCode> codes = goodsIdentificationCodeDao.getByArchivesId(goodsArchives.getArchivesId());
                goodsArchives.setIdentificationCodes(codes);
                return ResponseBuilder.success(goodsArchives);
            } else {
                return ResponseBuilder.fail("商品档案不存在！");
            }
        } catch (Exception e) {
            log.error(className + "getByCode", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<GoodsArchives> create(GoodsArchives goodsArchives) {
        try {
            Assert.notNull(goodsArchives);
            Assert.hasText(goodsArchives.getCode());
            goodsArchives.setBranchCode("SZ");
            Assert.notNull(goodsArchives.getCustomerCode());
            Assert.notNull(goodsArchives.getBranchCode());
            Assert.notEmpty(goodsArchives.getIdentificationCodes());

            if (goodsArchivesDao.exists(goodsArchives.getBranchCode(), goodsArchives.getCustomerCode(), goodsArchives.getCode(), null)) {
                return ResponseBuilder.fail("商品档案编码已存在！");
            }
            //检查识别码是否重复、已存在
            Response response = this.checkIdentificationCode(goodsArchives);
            if (!response.isSuccess()) {
                return response;
            }
            goodsArchives.setArchivesId(Snowflake.getInstance().next());
            if (goodsArchives.getLength() != null && goodsArchives.getWeight() != null && goodsArchives.getHeight() != null) {
                goodsArchives.setVolume(new BigDecimal(goodsArchives.getLength()).multiply(new BigDecimal(goodsArchives.getWeight())).multiply(new BigDecimal(goodsArchives.getHeight())).doubleValue());
            }
            Assert.isTrue(goodsArchivesDao.insert(goodsArchives) > 0);
            goodsArchives.getIdentificationCodes().forEach(identificationCode -> {
                identificationCode.setIdentificationCodeId(Snowflake.getInstance().next());
                identificationCode.setArchivesId(goodsArchives.getArchivesId());
            });
            Assert.isTrue(goodsIdentificationCodeDao.batchInsert(goodsArchives.getIdentificationCodes()) > 0);
            return ResponseBuilder.success(goodsArchives, "创建成功！");
        } catch (Exception e) {
            log.error(className + "create", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public ListResponse<GoodsArchives> batchCreate(List<GoodsArchives> goodsArchivesList) {
        try {
            Assert.notEmpty(goodsArchivesList);
            goodsArchivesList.forEach(goodsArchives -> {
                Assert.notNull(goodsArchives);
                Assert.hasText(goodsArchives.getCode());
                Assert.notNull(goodsArchives.getCustomerCode());
                Assert.notNull(goodsArchives.getBranchCode());
                Assert.notEmpty(goodsArchives.getIdentificationCodes());
            });
            String branchCode = goodsArchivesList.get(0).getBranchCode();
            List<GoodsArchives> goodsArchivesList1 = goodsArchivesDao.findByBranchCode(branchCode);
            List<String> existCodes = new ArrayList<>();
            for (GoodsArchives goodsArchives : goodsArchivesList) {
                boolean codeExist = false;
                for (GoodsArchives goodsArchives1 : goodsArchivesList1) {
                    if (goodsArchives.getCode().equals(goodsArchives1.getCode()) && goodsArchives.getCustomerCode().equals(goodsArchives1.getCustomerCode())) {
                        codeExist = true;
                        break;
                    }
                }
                if (codeExist) {
                    existCodes.add(goodsArchives.getCode());
                }
            }
            if (existCodes != null && existCodes.size() > 0) {
                return ResponseBuilder.listFail("商品档案编码" + String.join(",", existCodes) + "已存在");
            }
            List<GoodsIdentificationCode> identificationCodes = new ArrayList<>();
            goodsArchivesList.stream().forEach(
                    goodsArchives -> {
                        goodsArchives.setArchivesId(Snowflake.getInstance().next());
                        if (goodsArchives.getLength() != null && goodsArchives.getWeight() != null && goodsArchives.getHeight() != null) {
                            goodsArchives.setVolume(new BigDecimal(goodsArchives.getLength()).multiply(new BigDecimal(goodsArchives.getWeight())).multiply(new BigDecimal(goodsArchives.getHeight())).doubleValue());
                        }
                        goodsArchives.getIdentificationCodes().forEach(identificationCode -> {
                            identificationCode.setIdentificationCodeId(Snowflake.getInstance().next());
                            identificationCode.setArchivesId(goodsArchives.getArchivesId());
                        });
                        identificationCodes.addAll(goodsArchives.getIdentificationCodes());
                    }
            );
            Assert.isTrue(goodsArchivesDao.batchInsert(goodsArchivesList) > 0);
            Assert.isTrue(goodsIdentificationCodeDao.batchInsert(identificationCodes) > 0);
            return ResponseBuilder.list(goodsArchivesList);
        } catch (Exception e) {
            log.error(className + "batchCreate", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.listFail(e.getMessage());
        }
    }

    @Override
    public Response<GoodsArchives> update(GoodsArchives goodsArchives) {
        try {
            Assert.notNull(goodsArchives);
            Assert.hasText(goodsArchives.getCode());
            Assert.notNull(goodsArchives.getCustomerCode());
            Assert.notNull(goodsArchives.getBranchCode());
            Assert.hasText(goodsArchives.getArchivesId());
            Assert.notEmpty(goodsArchives.getIdentificationCodes());

            if (goodsArchivesDao.exists(goodsArchives.getBranchCode(), goodsArchives.getCustomerCode(), goodsArchives.getCode(), goodsArchives.getArchivesId())) {
                return ResponseBuilder.fail("商品档案编码已存在！");
            }

            //检查识别码是否重复、已存在
            Response response = this.checkIdentificationCode(goodsArchives);
            if (!response.isSuccess()) {
                return response;
            }

            if (goodsArchives.getLength() != null && goodsArchives.getWeight() != null && goodsArchives.getHeight() != null) {
                goodsArchives.setVolume(new BigDecimal(goodsArchives.getLength()).multiply(new BigDecimal(goodsArchives.getWeight())).multiply(new BigDecimal(goodsArchives.getHeight())).doubleValue());
            }
            Assert.isTrue(goodsArchivesDao.update(goodsArchives) > 0);
            Assert.isTrue(goodsIdentificationCodeDao.deleteByArchivesId(goodsArchives.getArchivesId()) > 0);
            goodsArchives.getIdentificationCodes().forEach(identificationCode -> {
                identificationCode.setIdentificationCodeId(Snowflake.getInstance().next());
                identificationCode.setArchivesId(goodsArchives.getArchivesId());
            });
            Assert.isTrue(goodsIdentificationCodeDao.batchInsert(goodsArchives.getIdentificationCodes()) > 0);
            return ResponseBuilder.success(goodsArchives, "修改成功！");
        } catch (Exception e) {
            log.error(className + "update", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<GoodsArchivesDto> pageList(int startPage, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(startPage);
            Assert.notNull(pageSize);
            PageHelper.startPage(startPage, pageSize);
            PageInfo<GoodsArchivesDto> pageInfo = new PageInfo<>(goodsArchivesDao.pageList(params));
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return (PageResponse) ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response batchDelete(List<String> archivesIds) {
        try {
            Assert.notNull(archivesIds);
            Assert.isTrue(goodsArchivesDao.batchDelete(archivesIds) > 0);
            Assert.isTrue(goodsIdentificationCodeDao.batchDelByArchivesIds(archivesIds) > 0);
            return ResponseBuilder.success();
        } catch (Exception e) {
            log.error(className + "batchDelete", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public ListResponse<GoodsArchives> getByBranchCodeAndCustomerCode(String branchCode, String customerCode) {
        try {
            Assert.notNull(branchCode);
            Assert.notNull(customerCode);
            List<GoodsArchives> goodsArchives = goodsArchivesDao.findByBranchCodeAndCustomerCode(branchCode, customerCode);
            return ResponseBuilder.list(goodsArchives);
        } catch (Exception e) {
            log.error(className + "getByBranchCodeAndCustomerCode", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.listFail(e.getMessage());
        }
    }

    @Override
    public Response<GoodsArchives> getByIdentificationCode(String branchCode, String identificationCode) {
        try {
            Assert.notNull(branchCode);
            Assert.notNull(identificationCode);
            GoodsArchives goodsArchives = goodsArchivesDao.getByIdentificationCode(branchCode, identificationCode);
            if (goodsArchives != null) {
                return ResponseBuilder.success(goodsArchives);
            }
            return ResponseBuilder.fail("识别码未找到相应的商品档案！");
        } catch (Exception e) {
            log.error(className + "getByIdentificationCode", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Object importGoodsArchives(InputStream inputStream, String currentBranchCode) {
        return null;//// TODO: 2017/1/5
    }

    private Response checkIdentificationCode(GoodsArchives goodsArchives) {
        Assert.notNull(goodsArchives);
        List<GoodsIdentificationCode> identificationCodes = goodsArchives.getIdentificationCodes();
        Assert.notEmpty(identificationCodes);
        Response response = ResponseBuilder.success();
        Set<String> repeatCode = new HashSet<>();
        for (int i = 0; i < identificationCodes.size() - 1; i++) {
            String identificationCode = identificationCodes.get(i).getIdentificationCode();
            Assert.notNull(identificationCode);
            for (int j = i + 1; j < identificationCodes.size(); j++) {
                String identificationCode1 = identificationCodes.get(j).getIdentificationCode();
                Assert.notNull(identificationCode1);
                if (identificationCode.equals(identificationCode1)) {
                    repeatCode.add(identificationCode);
                }
            }
        }
        if (repeatCode.size() > 0) {
            response.setSuccess(false);
            response.setMessage("识别码" + String.join(",", repeatCode) + "重复！");
            return response;
        }
        Set<String> existsCode = new HashSet<>();
        for (GoodsIdentificationCode identificationCode : identificationCodes) {
            Boolean isExists = goodsArchivesDao.identificationCodeExists(goodsArchives.getBranchCode(), identificationCode.getIdentificationCode(), goodsArchives.getArchivesId());
            if (isExists) {
                existsCode.add(identificationCode.getIdentificationCode());
            }
        }
        if (existsCode.size() > 0) {
            response.setSuccess(false);
            response.setMessage("识别码" + String.join(",", existsCode) + "已存在于其他的商品档案！");
        }
        return response;
    }
}
