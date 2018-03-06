package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.OrganizationService;
import com.lnet.model.ums.organization.Organization;
import com.lnet.ums.mapper.dao.OrganizationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {
    private final String className = this.getClass().getSimpleName() + ".";
    @Resource
    private OrganizationDao organizationDao;

    @Override
    public boolean exists(String organizationCode) {
        try {
            Assert.hasText(organizationCode);
            return organizationDao.exists(organizationCode, null);
        } catch (Exception e) {
            log.error(className + "exists", e);
            return false;
        }
    }

    @Override
    public Response<Organization> create(Organization organization) {
        try {
            Assert.notNull(organization);
            Assert.hasText(organization.getCode());
            Assert.hasText(organization.getName());
            Assert.hasText(organization.getSystemCode());
            // TODO: 2016/7/14 validate  parentId  exists.
            // Assert.isTrue(organizationDao.exists( organization.getParentId()));

            organization.setOrganizationId(Snowflake.getInstance().next());

            if(!StringUtils.isEmpty(organization.getParentId())) {
                Optional<Organization> optionalParent = Optional.ofNullable(organizationDao.get(organization.getParentId()));
                validateType(organization, optionalParent);
            }


            Assert.isTrue(!organizationDao.exists(organization.getCode(), null), "编码已存在!");

            boolean isSuccess = organizationDao.insert(organization) > 0;
            if(isSuccess)
                return ResponseBuilder.success(organization, "创建成功！");
            return ResponseBuilder.fail("创建失败！");

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response delete(String organizationId) {
        try {
            Assert.hasText(organizationId);
            //dao 删除的时候会直接递归删除
            int result = organizationDao.deleteById(organizationId);

            boolean isSuccess = result > 0;

            if(isSuccess)
                return ResponseBuilder.success("删除成功！");
            return ResponseBuilder.fail("删除失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "delete", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Organization> update(Organization organization) {
        try {
            Assert.notNull(organization);
            Assert.hasText(organization.getCode());
            Assert.hasText(organization.getName());
//            Assert.hasText(organization.getSystemCode());

            Optional<Organization> optionalParent = Optional.ofNullable(organizationDao.get(organization.getParentId()));

            validateType(organization, optionalParent);

            Assert.isTrue(!organizationDao.exists(organization.getCode(), organization.getOrganizationId()), "编码已存在！");

            boolean isSuccess = organizationDao.update(organization) > 0;
            if(isSuccess)
                return ResponseBuilder.success(organization, "修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String code) {
        try {
            Assert.hasText(code);
            if(organizationDao.activate(code) > 0)
                return ResponseBuilder.success("", "启用成功！");
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "activate", e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response activate(String[] codes) {
        try {
            Assert.notNull(codes);

            boolean isSuccess = false;
            for (String code : codes) {
                isSuccess = organizationDao.activate(code) > 0;
            }
            return isSuccess ? ResponseBuilder.success(null, "启用成功!") : ResponseBuilder.fail("启用失败!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "activate", e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            Assert.hasText(code);

            if(organizationDao.inactivate(code) > 0)
                return ResponseBuilder.success("禁用成功！");
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "inactivate", e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response inactivate(String[] codes) {
        try {
            Assert.notNull(codes);

            boolean isSuccess = false;
            for (String code : codes) {
                isSuccess = organizationDao.inactivate(code) > 0;
            }
            return isSuccess ? ResponseBuilder.success(null, "禁用成功!") : ResponseBuilder.fail("禁用失败!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "inactivate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Organization> get(String organizationId) {
        try {
            Assert.hasText(organizationId);

            Optional<Organization> orgOptional = Optional.ofNullable(organizationDao.get(organizationId));
            return orgOptional.isPresent() ? ResponseBuilder.success(orgOptional.get(), "获取成功!") : ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "get", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Organization> getByCode(String organizationCode) {
        try {
            Assert.hasText(organizationCode);

            Optional<Organization> orgOptional = Optional.ofNullable(organizationDao.getByCode(organizationCode));
            return orgOptional.isPresent() ? ResponseBuilder.success(orgOptional.get(), "获取成功!") : ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "getByCode", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Organization>> getHierarchical(String code) {
        try {
            Assert.hasText(code);
            List<Organization> organizationList = organizationDao.getHierarchical(code);

            if (organizationList != null && organizationList.size() > 0) {
                return ResponseBuilder.success(this.setChildren(organizationList));
            }
            return ResponseBuilder.fail("失败！");
        } catch (Exception e) {
            log.error(className + "getHierarchical", e);
            return ResponseBuilder.fail(e.getMessage());
        }

    }

    @Override
    public Response<List<Organization>> getAvailableHierarchical(String code) {
        try {
            Assert.hasText(code);
            List<Organization> organizationList = organizationDao.getAvailableHierarchical(code);
            if (organizationList != null && organizationList.size() > 0) {
                return ResponseBuilder.success(this.setChildren(organizationList));
            }
            return ResponseBuilder.fail("失败！");
        } catch (Exception e) {
            log.error(className + "getAvailableHierarchical", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Organization>> getAllBranches(String code) {
        try {
            Assert.hasText(code);

            List<Organization> organizations = organizationDao.getAvailableHierarchical(code);
            if (organizations != null && organizations.size() > 0) {
                List<Organization> allBranches = organizations.stream()
                        .filter(f -> f.getType().equals(Organization.type.BRANCH)).collect(Collectors.toList());
                return ResponseBuilder.success(allBranches);
            }

            return ResponseBuilder.fail("失败！");
        } catch (Exception e) {
            log.error(className + "getAllBranches", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<Organization> pageList(int pageNumber, int pageSize, Map<String, Object> params) {
        try {

            PageHelper.startPage(pageNumber, pageSize);
            PageInfo<Organization> pageInfo = new PageInfo<>(organizationDao.pageList(params));
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e.getMessage());
            return (PageResponse) ResponseBuilder.fail(e.getMessage());
        }
    }

    /**
     * 验证类别
     *
     * @param organization
     * @param optionalParent
     */
    private void validateType(Organization organization, Optional<Organization> optionalParent) {
        switch (organization.getType()) {
            case GROUP:
                Assert.isNull(organization.getParentId(), "集团没有父级!");
                break;
            case REGION:
                if (optionalParent.isPresent()) {
                    Organization parent = optionalParent.get();
                    Assert.isTrue(Organization.type.GROUP.equals(parent.getType()), "区域的父级必须是集团!");
                }
                break;
            case BRANCH:
                if (optionalParent.isPresent()) {
                    Organization parent = optionalParent.get();
                    Assert.isTrue(Organization.type.REGION.equals(parent.getType()), "分公司(站点)的父级必须是区域!");
                }
                break;
            case SUBCO:
                if (optionalParent.isPresent()) {
                    Organization parent = optionalParent.get();
                    Assert.isTrue(Organization.type.BRANCH.equals(parent.getType()), "子公司的父级必须是分公司(站点)!");
                }
                break;
            case DEPT:
                if (optionalParent.isPresent()) {
                    Organization parent = optionalParent.get();
                    Assert.isTrue(Organization.type.BRANCH.equals(parent.getType())
                            && Organization.type.SUBCO.equals(parent.getType()), "部门的父级必须是分公司(站点)或子公司!");
                }
                break;
            case TEAM:
                if (optionalParent.isPresent()) {
                    Organization parent = optionalParent.get();
                    Assert.isTrue(Organization.type.DEPT.equals(parent.getType()), "小组的父级必须是部门!");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 为子级赋值
     *
     * @param organizationList
     * @return
     */
    private List<Organization> setChildren(List<Organization> organizationList) {
        //set items
        organizationList.parallelStream().forEach(f -> {
            f.setItems(organizationList.stream()
                    .filter(ft -> ft.getParentId() != null && ft.getParentId().equals(f.getOrganizationId()))
                    .collect(Collectors.toList()));
        });
        //filter
        return organizationList.stream()
                .filter(f -> f.getParentId() == null && f.getType().equals(Organization.type.GROUP))
                .collect(Collectors.toList());
    }
}
