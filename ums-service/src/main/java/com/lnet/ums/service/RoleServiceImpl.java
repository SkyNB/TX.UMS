package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.RoleService;
import com.lnet.ums.contract.model.Permission;
import com.lnet.model.ums.role.Role;
import com.lnet.ums.mapper.dao.RoleDao;
import com.lnet.ums.mapper.dao.UserRoleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    final String errorMessage = "创建默认角色失败！";
    private final String className = this.getClass().getSimpleName() + ".";
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public boolean exists(String code) {
        try {
            Assert.hasText(code);
            return roleDao.exists(code, null);
        } catch (Exception e) {
            log.error(className + "exists", e);
            return false;
        }
    }

    @Override
    public Response<Role> create(Role role) {
        try {
            Assert.notNull(role);
            Assert.hasText(role.getCode());
            Assert.hasText(role.getName());
            Assert.hasText(role.getSystemCode());

            role.setRoleId(Snowflake.getInstance().next());

            Assert.isTrue(!roleDao.exists(role.getCode(), null), "角色编码已经存在!");
            // TODO: 原来RoleModel中的List<>permission改到了Role中
            role.setPermission(String.join(",", role.getPermissionsLists()));

            boolean isSuccess = roleDao.insert(role) > 0;

            if (isSuccess)
                return ResponseBuilder.success(role, "创建成功！");
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Role> update(Role role) {
        try {
            Assert.notNull(role);
            Assert.hasText(role.getCode());
            Assert.hasText(role.getName());
            Assert.hasText(role.getSystemCode());

            Assert.isTrue(!roleDao.exists(role.getCode(), role.getRoleId()), "角色编码已经存在!");

            role.setPermission(String.join(",", role.getPermissionsLists()));

            Optional<Role> currentRole = Optional.ofNullable(roleDao.get(role.getRoleId()));
            if (currentRole.isPresent()) {
                Assert.isTrue(currentRole.get().isSysRole() == false, "系统预设角色不能修改!");
            }

            boolean isSuccess = roleDao.update(role) > 0;
            if (isSuccess)
                return ResponseBuilder.success(role, "修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response delete(String roleId) {
        try {
            Assert.hasText(roleId);

            Optional<Role> currentRole = Optional.ofNullable(roleDao.get(roleId));
            if (currentRole.isPresent()) {
                userRoleDao.deleteByRoleCode(currentRole.get().getCode());
            }

            boolean isSuccess = roleDao.deleteById(roleId) > 0;

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
    public Response activate(String code) {
        try {
            Assert.hasText(code);

            if (roleDao.activate(code) > 0)
                return ResponseBuilder.success("启用成功！");
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "activate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String[] codes) {
        try {
            Assert.notNull(codes);

            boolean isSuccess = false;
            for (String code : codes) {
                isSuccess = roleDao.activate(code) > 0;
            }

            return isSuccess ? ResponseBuilder.success(null, "启用成功!") : ResponseBuilder.fail("启用失败!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "activate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            Assert.hasText(code);

            if (roleDao.inactivate(code) > 0)
                return ResponseBuilder.success("禁用成功！");
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "inactivate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String[] codes) {
        try {
            Assert.notNull(codes);
            boolean isSuccess = false;
            for (String code : codes) {
                isSuccess = roleDao.inactivate(code) > 0;
            }
            return isSuccess ? ResponseBuilder.success(null, "禁用成功!") : ResponseBuilder.fail("禁用失败!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "inactivate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Role> get(String roleId) {
        try {
            Assert.hasText(roleId);

            Optional<Role> optionRole = Optional.ofNullable(roleDao.get(roleId));

            List<String> permissions = new ArrayList<>();
            if (optionRole.isPresent()) {
                Role role = optionRole.get();
                if (role.getPermission() != null) {
                    role.setPermissionsLists(Arrays.asList(role.getPermission().split(",")));
                }

                if (role.getPermissionsLists() != null && role.getPermissionsLists().size() > 0) {
                    role.getPermissionsLists().forEach(permission -> {
                        if ("*".equals(permission)) {
                            for (Permission permission1 : Permission.values()) {
                                permissions.add(permission1.getCode());
                            }
                            role.setPermissionsLists(permissions);
                        }
                    });
                }
                return ResponseBuilder.success(role, null);
            }
            return ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "getBindings", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Role> getByCode(String code) {
        try {
            Assert.hasText(code);

            Optional<Role> roleOptional = Optional.ofNullable(roleDao.getByCode(code));
            return roleOptional.isPresent() ? ResponseBuilder.success(roleOptional.get(), "获取成功!") : ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "getByCode", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Role>> getAll(String systemCode) {
        try {
            return ResponseBuilder.success(roleDao.getAll(systemCode));
        } catch (Exception e) {
            log.error(className + "getAll", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Role>> getAvailable(String systemCode) {
        try {
            return ResponseBuilder.success(roleDao.getAvailable(systemCode));
        } catch (Exception e) {
            log.error(className + "getAvailable", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<Role> pageList(int pageNumber, int pageSize, Map<String, Object> params) {
        try {
            PageHelper.startPage(pageNumber, pageSize);
            PageInfo<Role> pageInfo = new PageInfo<>(roleDao.pageList(params));
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e.getMessage());
            return (PageResponse) ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response createDefaultRole(List<Role> roles) {
        //设置ID
        roles.forEach(e -> {
            e.setRoleId(Snowflake.getInstance().next());
        });

        //批量插入
        boolean isSuccess = roleDao.batchInsert(roles) == roles.size();

        if (!isSuccess) {
            //失败则回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(errorMessage);
        } else {
            return ResponseBuilder.success();
        }
    }
}
