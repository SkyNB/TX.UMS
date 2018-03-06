package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.BusinessGroupService;
import com.lnet.model.ums.customer.customerEntity.BusinessGroup;
import com.lnet.ums.mapper.dao.BusinessGroupDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@Slf4j
public class BusinessGroupServiceImpl implements BusinessGroupService {
    private final String className = BusinessGroupServiceImpl.class.getSimpleName() + ".";

    @Autowired
    private BusinessGroupDao businessGroupDao;

    @Override
    public Boolean exists(String code, String businessGroupId) {
        try {
            Assert.hasText(code);

            return businessGroupDao.exists(code, businessGroupId);
        } catch (Exception e) {
            log.error(className + "exists", e.getMessage());
            return true;
        }
    }

    @Override
    public Response create(BusinessGroup businessGroup) {
        try {
            Assert.hasText(businessGroup.getCode());

            boolean isExists = exists(businessGroup.getCode(), null);
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            //完善业务组信息
            businessGroup.setBusinessGroupId(Snowflake.getInstance().next());
            businessGroup.setCreateTime(LocalDateTime.now());

            boolean isSuccess = businessGroupDao.insert(businessGroup) > 0;
            if (isSuccess)
                return ResponseBuilder.success();
            return ResponseBuilder.fail("新增失败！");
        } catch (Exception e) {
            log.error(className + "insert", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response update(BusinessGroup businessGroup) {
        try {
            Assert.hasText(businessGroup.getBusinessGroupId());

            businessGroup.setModifyTime(LocalDateTime.now());

            boolean isSuccess = businessGroupDao.update(businessGroup) > 0;
            if (isSuccess)
                return ResponseBuilder.success();
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            log.error(className + "update", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<BusinessGroup> getByCode(String code) {
        try {
            Assert.hasText(code);
            BusinessGroup businessGroup = businessGroupDao.getByCode(code);

            if (null != businessGroup)
                return ResponseBuilder.success(businessGroup);
            return ResponseBuilder.fail("获取失败！");
        } catch (Exception e) {
            log.error(className + "getByCode", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<BusinessGroup>> getAll() {
        try {
            List<BusinessGroup> businessGroups = businessGroupDao.getAll();

            Assert.notEmpty(businessGroups, "暂无业务组信息！");
            return ResponseBuilder.success(businessGroups);
        } catch (Exception e) {
            log.error(className + "getAll", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<BusinessGroup> pageList(int pageNumber, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(pageNumber);
            Assert.notNull(pageSize);
            PageHelper.startPage(pageNumber, pageSize);

            List<BusinessGroup> businessGroups = businessGroupDao.pageList(params);
            PageInfo<BusinessGroup> pageInfo = new PageInfo<>(businessGroups);
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e.getMessage());
            return (PageResponse) ResponseBuilder.fail(e);
        }
    }
}
