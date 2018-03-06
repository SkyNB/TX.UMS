package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.SiteService;
import com.lnet.model.ums.site.Site;
import com.lnet.ums.mapper.dao.SiteDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class SiteServiceImpl implements SiteService {

    private final String className = this.getClass().getSimpleName() + ".";
    @Resource
    private SiteDao siteDao;

    @Override
    public boolean exists(String code) {
        try {
            Assert.hasText(code);

            return siteDao.exists(code, null);
        } catch (Exception e) {
            log.error(className + "exists", e);
            return false;
        }
    }

    @Override
    public Response<Site> create(Site site) {
        try {
            Assert.notNull(site);
            Assert.hasText(site.getCode());
            Assert.hasText(site.getName());
            Assert.hasText(site.getBranchCode());

            site.setSiteId(Snowflake.getInstance().next());

            Assert.isTrue(!siteDao.exists(site.getCode(), null), "站点编码已经存在!");

            boolean isSuccess = siteDao.insert(site) > 0;
            if (isSuccess)
                return ResponseBuilder.success(site, "创建成功！");
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Site> update(Site site) {
        try {
            Assert.notNull(site);
            Assert.hasText(site.getCode());
            Assert.hasText(site.getName());
            Assert.hasText(site.getBranchCode());

            Assert.isTrue(!siteDao.exists(site.getCode(), site.getSiteId()), "站点编码已经存在!");

            boolean isSuccess = siteDao.update(site) > 0;
            if (isSuccess)
                return ResponseBuilder.success(site, "修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response delete(String siteId) {
        try {
            Assert.hasText(siteId);

            boolean isSuccess = siteDao.deleteById(siteId) > 0;

            if (isSuccess)
                return ResponseBuilder.success("删除成功！");
            return ResponseBuilder.fail("删除失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "delete", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Site> get(String siteId) {
        try {
            Assert.hasText(siteId);

            Optional<Site> siteOptional = Optional.ofNullable(siteDao.get(siteId));
            return siteOptional.isPresent() ? ResponseBuilder.success(siteOptional.get(), "获取成功!") : ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "get", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Site> getByCode(String code) {
        try {
            Assert.hasText(code);

            Optional<Site> siteOptional = Optional.ofNullable(siteDao.getByCode(code));
            return siteOptional.isPresent() ? ResponseBuilder.success(siteOptional.get(), "获取成功!") : ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "getByCode", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Site>> getByBranchCode(String branchCode) {
        try {
            Assert.hasText(branchCode);

            return ResponseBuilder.success(siteDao.getByBranchCode(branchCode));
        } catch (Exception e) {
            log.error(className + "getAll", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<Site> pageList(int pageNumber, int pageSize, Map<String, Object> params) {
        try {
            PageHelper.startPage(pageNumber, pageSize);
            PageInfo<Site> pageInfo = new PageInfo<>(siteDao.pageList(params));
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e.getMessage());
            return (PageResponse) ResponseBuilder.fail(e);
        }
    }

    @Override
    public ListResponse<Site> findByAddressBranch(String districtCode, String branchCode) {
        try {
            Assert.hasText(districtCode);
            Assert.hasText(branchCode);
            return ResponseBuilder.list(siteDao.findByAddressBranch(districtCode, branchCode));
        } catch (Exception e) {
            log.error(className + "pageList", e.getMessage());
            return ResponseBuilder.listFail(e);
        }
    }

    @Override
    public ListResponse<Site> findAll() {
        try {
            return ResponseBuilder.list(siteDao.findAll());
        } catch (Exception e) {
            log.error(className + "findAll", e.getMessage());
            return ResponseBuilder.listFail(e);
        }
    }
}
