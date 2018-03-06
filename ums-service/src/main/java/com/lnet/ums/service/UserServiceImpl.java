package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.PasswordStorage;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.UserService;
import com.lnet.ums.contract.model.ObligateUserName;
import com.lnet.model.ums.organization.Organization;
import com.lnet.model.ums.role.Role;
import com.lnet.model.ums.site.Site;
import com.lnet.model.ums.user.User;
import com.lnet.model.ums.user.UserBinding;
import com.lnet.model.ums.userrole.UserRole;
import com.lnet.ums.mapper.dao.*;
import com.lnet.ums.mapper.dao.mappers.RoleMapper;
import com.lnet.ums.mapper.dao.mappers.UserMapper;
import com.lnet.ums.mapper.dao.mappers.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
 /*   @Resource
    private Validator validator;

    @Override
    public Response<User> create(UserCreateModel model) {

        Errors errors = new BeanPropertyBindingResult(model, UserCreateModel.class.getName());
        validator.validate(model, errors);

        //Set<ConstraintViolation<UserCreateModel>> result = validator.validate(model);
        User newUser = User.builder()
                .userName(model.getUserName())
                .isActive(true)
                .displayName(model.getDisplayName())
                .password(model.getPassword())
                .email(model.getEmail())
                .build();

      //  return Response.<User>builder().data(newUser).build();
        return new Response<>(true,newUser,"");
    }*/

    private final String className = this.getClass().getSimpleName() + ".";
    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserBindingDao userBindingDao;
    @Resource
    private OrganizationDao organizationDao;
    @Autowired
    private SiteDao siteDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Response<User> create(User user) {
        try {
            Assert.notNull(user);
            Assert.hasText(user.getUsername());

            //Assert.isNull(ObligateUserName.valueOf(user.getUserName().toUpperCase()), "系统预留用户名");
            //判断是否为系统预留用户名
            for (ObligateUserName userName : ObligateUserName.values()) {
                if (userName.toString().equalsIgnoreCase(user.getUsername()))
                    return ResponseBuilder.fail("系统预留用户名！");
            }

            if (!userDao.exists(user.getUsername().toUpperCase(), null)) {

                user.setUserId(Snowflake.getInstance().next());
                user.setUsername(user.getUsername().toUpperCase());
                //userRoles
                List<UserRole> userRoles = new ArrayList<>();
                if (user.getRoleCodes() != null && user.getRoleCodes().size() > 0) {
                    user.getRoleCodes().forEach(roleCode -> {
                        UserRole userRole = UserRole.builder().userId(user.getUserId())
                                .roleCode(roleCode)
                                .build();
                        userRoles.add(userRole);
                    });
                }

                user.setPassword(PasswordStorage.createHash(user.getPassword()));
                user.setCreateTime(LocalDateTime.now());

                boolean isSuccess = userDao.insert(user) > 0;
                if (userRoles != null && userRoles.size() > 0) {
                    Assert.isTrue(userRoleDao.batchInsert(userRoles) == userRoles.size());
                }

                if (isSuccess)
                    return ResponseBuilder.success(user, "创建成功!");
                return ResponseBuilder.fail("创建失败！");
            } else {
                return ResponseBuilder.fail("用户名不能重复！");
            }

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<User> update(User user) {

        try {
            Assert.notNull(user);
            Assert.hasText(user.getUsername());

            //用户类型不能修改

            Assert.isTrue(!userDao.exists(user.getUsername().toUpperCase(), user.getUserId()), "用户名不能重复!");
            user.setUsername(user.getUsername().toUpperCase());
            if (User.type.DRIVER.equals(user.getType())) {
                user.getRoleCodes().forEach(f -> {
                    Assert.isTrue(user.getRoleCodes().equals(User.type.DRIVER), "司机只能拥有司机的角色!");
                });
            }
            List<UserRole> userRoles = new ArrayList<>();
            if (user.getRoleCodes() != null && user.getRoleCodes().size() > 0) {
                user.getRoleCodes().forEach(roleCode -> {
                    UserRole userRole = UserRole.builder().userId(user.getUserId())
                            .roleCode(roleCode)
                            .build();
                    userRoles.add(userRole);
                });
            }
            userRoleDao.deleteByUserId(user.getUserId());
            boolean isSuccess = userDao.update(user) > 0;
            if (userRoles != null && userRoles.size() > 0) {
                userRoleDao.batchInsert(userRoles);
            }

            if (isSuccess)
                return ResponseBuilder.success(user, "修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response delete(String userId) {
        try {
            Assert.hasText(userId);

            userRoleDao.deleteByUserId(userId);
            boolean isSuccess = userDao.deleteById(userId) > 0;

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
    public Response<User> get(String userId) {
        try {
            Assert.hasText(userId);

            Optional<User> userOptional = Optional.ofNullable(userDao.get(userId));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setRoleCodes(userRoleDao.getRoleCodesByUserId(userId));

                //getBindings userBindings.
                List<UserBinding> bindings = userBindingDao.getBindings(user.getUserId());
                if (bindings != null && bindings.size() > 0) {
                    user.setSiteCodes(new ArrayList<>());
                    bindings.forEach(f -> {
                        if (f.getBindingType().equals(UserBinding.bindingType.ORGANIZATION)) {
                            user.setOrgCode(f.getBindingValue());
                        }
                        if (f.getBindingType().equals(UserBinding.bindingType.SYSTEM)) {
                            user.setSystemCode(f.getBindingValue());
                        }
                        if (f.getBindingType().equals(UserBinding.bindingType.SITE)) {
                            user.getSiteCodes().add(f.getBindingValue());
                        }
                        //others
                    });

                }
                return ResponseBuilder.success(user, "获取成功!");
            }
            return ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "getBindings", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<User> getByUserName(String userName) {
        try {
            Assert.hasText(userName);

            Optional<User> userOptional = Optional.ofNullable(userMapper.getByUsername(userName.toUpperCase()));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setRoleCodes(userRoleMapper.getRoleCodesByUserId(user.getUserId()));

                List<Role> roles = new ArrayList<>();
                user.getRoleCodes().forEach(code -> {
                    Optional<Role> roleOptional = Optional.ofNullable(roleMapper.getByCode(code));
                    if (roleOptional.isPresent()) {
                        roles.add(roleOptional.get());
                    }
                });
                user.setRoles(roles);
                return ResponseBuilder.success(user, "获取成功!");
            }
            return ResponseBuilder.fail("获取失败!");

        } catch (Exception e) {
            log.error(className + "getByUserName", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<User>> getAvailable(String systemCode) {
        try {
            return ResponseBuilder.success(userDao.getAvailable(systemCode));
        } catch (Exception e) {
            log.error(className + "getAvailable", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public boolean exists(String userName) {
        try {
            Assert.hasText(userName);
            return userDao.exists(userName.toUpperCase(), null);
        } catch (Exception e) {
            log.error(className + "exists", e);
            return false;
        }
    }

    @Override
    public Response modifyPassword(String username, String oldPassword, String newPassword) {
        try {
            Assert.hasText(username);
            Assert.hasText(oldPassword);
            Assert.hasText(newPassword);

            Optional<User> currentUser = Optional.ofNullable(userDao.getByUsername(username));
            if (currentUser.isPresent()) {
                Assert.isTrue(PasswordStorage.verifyPassword(oldPassword, currentUser.get().getPassword()), "旧密码不正确!");

                newPassword = PasswordStorage.createHash(newPassword);
                boolean isSuccess = userDao.updatePassword(username, newPassword) > 0;
                return ResponseBuilder.success("修改成功！");
            }

            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "modifyPassword", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response resetPassword(List<String> userIds, String newPassword) {
        try {
            Assert.notNull(userIds);
            Assert.hasText(newPassword);

            newPassword = PasswordStorage.createHash(newPassword);

            boolean isSuccess = false;
            //SQL IN Clause 1000 item limit
            final int boundary = 1000;
            if (userIds != null && userIds.size() >= boundary) {
                int tmpResult = 0;
                for (int i = 0; i <= userIds.size() / boundary; i++) {
                    int limitSize = userIds.size() > i * boundary ? boundary : userIds.size() - (i * boundary);
                    List<String> tmpIds = userIds.stream()
                            .skip(i * boundary)
                            .limit(limitSize).collect(Collectors.toList());
                    if (tmpIds != null && tmpIds.size() > 0) {
                        tmpResult += userDao.resetPassword(userIds, newPassword);
                    }
                }
                isSuccess = tmpResult >= userIds.size();
            } else {
                isSuccess = userDao.resetPassword(userIds, newPassword) >= userIds.size();
            }

            return ResponseBuilder.success("修改成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "batchModifyPassword", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public String getUserName(String userId) {

        try {
            Assert.hasText(userId);

            Optional<User> currentUser = Optional.ofNullable(this.get(userId).getBody());
            if (currentUser.isPresent()) {
                return currentUser.get().getUsername();
            }
            return null;
        } catch (Exception e) {
            log.error(className + "getUserName", e);
            return null;
        }
    }

    @Override
    public PageResponse<User> pageList(int pageNumber, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(pageNumber);
            Assert.notNull(pageSize);

            PageHelper.startPage(pageNumber, pageSize);
            List<User> users = userDao.pageList(params);

            //属性填充
            users.forEach(ele -> {
                UserBinding binding = userBindingDao.getBinding(ele.getUserId(), UserBinding.bindingType.ORGANIZATION.toString());
                // TODO: 2017/2/24 注解掉了
                /*if (null != binding) {
                    Organization organization = organizationDao.getByCode(binding.getBindingValue());
                    if (null != organization)
                        ele.setOrgName(organization.getName());
                }*/
            });

            PageInfo<User> pageInfo = new PageInfo<>(users);
            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error(className + "pageList", e.getMessage());
            return ResponseBuilder.pageFail(e.getMessage());
        }
    }

    @Override
    public Response bind(String userId, UserBinding.bindingType bindingType, String... bindingValues) {
        try {
            Assert.hasText(userId);
            Assert.notNull(bindingType);
            //Assert.notEmpty(bindingValues);

            boolean isSuccess = false;

            //check exists
            boolean merge = false;
            //是否只能绑定一个属性
            switch (bindingType) {
                case ORGANIZATION:
                    merge = true;
                    break;
                case SYSTEM:
                    merge = true;
                    break;
                case SITE:
                    merge = false;
                    break;
                default:
                    merge = false;
                    break;
            }

            if (merge) {
                Assert.hasText(bindingValues[0], "绑定值不能为空！");
                boolean exists = userBindingDao.exists(userId, bindingType.name());
                if (exists) {
                    isSuccess = userBindingDao.update(userId, bindingValues[0]) > 0;
                } else {
                    UserBinding userBinding = UserBinding.builder()
                            .userId(userId)
                            .bindingType(bindingType)
                            .bindingValue(bindingValues[0])
                            .build();
                    isSuccess = userBindingDao.bind(userBinding) > 0;
                }

            } else {
                //删除原有的绑定值
                userBindingDao.deleteByUserIdAndType(userId, bindingType.name());
                isSuccess = true;
                if (null != bindingValues) {
                    for (String bindingValue : bindingValues) {
                        UserBinding userBinding = UserBinding.builder()
                                .userId(userId)
                                .bindingType(bindingType)
                                .bindingValue(bindingValue)
                                .build();
                        isSuccess = userBindingDao.bind(userBinding) > 0;//失败事务应该回滚
                    }
                }
            }

            return isSuccess ? ResponseBuilder.success(null, "绑定成功!") : ResponseBuilder.fail("绑定失败!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "bind", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response unBind(String userId, UserBinding.bindingType bindingType) {
        try {
            Assert.hasText(userId);
            Assert.notNull(bindingType);

            boolean isSuccess = userBindingDao.unBind(userId, bindingType.name()) > 0;
            if (isSuccess)
                return ResponseBuilder.success("解除绑定成功！");
            return ResponseBuilder.fail("解除绑定失败！");
        } catch (Exception e) {
            log.error(className + "unBind", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response getBindings(String userId) {
        try {
            Assert.hasText(userId);
            Map<String, String> map = new HashMap<>();

            //按绑定的类型分组
            Map<String, List<UserBinding>> group = userBindingDao.getBindings(userId).stream().collect(Collectors.groupingBy(e -> e.getBindingType().toString()));

            group.entrySet().forEach(e -> {
                if (e.getValue() != null && e.getValue().size() > 0) {
                    map.put(e.getKey(), e.getValue().get(0).getBindingValue());
                }

            });

            return ResponseBuilder.success(map);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Site>> getAllSite(String userId) {
        try {
            Assert.hasText(userId);

            //按绑定的类型分组
            Map<String, List<UserBinding>> group = userBindingDao.getBindings(userId).stream().collect(Collectors.groupingBy(e -> e.getBindingType().toString()));

            //获取绑定的站点code
            List<UserBinding> bindings = group.get("SITE");

            //获取所有的站点信息
            List<Site> sites = bindings.stream().map(m -> {
                return siteDao.getByCode(m.getBindingValue());
            }).collect(Collectors.toList());

            return ResponseBuilder.success(sites);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response validate(String userName, String password) {
        try {
            Assert.hasText(userName);
            Assert.hasText(password);

            Optional<User> targetUser = Optional.ofNullable(userDao.getByUsername(userName.toUpperCase()));
            if (targetUser.isPresent()) {
                //boolean isValidate = PasswordStorage.verifyPassword(password, targetUser.get().getPassword());
                boolean isValidate = true;
                if (isValidate)
                    return ResponseBuilder.success("密码正确！");
                return ResponseBuilder.fail("密码错误！");
            } else {
                return ResponseBuilder.fail("用户名不存在!");
            }
        } catch (Exception e) {
            log.error(className + "validate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(List<String> userIds) {
        try {
            Assert.notEmpty(userIds);
            boolean isSuccess = userDao.inactivate(userIds) == userIds.size();
            if (isSuccess)
                return ResponseBuilder.success("禁用成功！");
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error(className + "inactivate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(List<String> userIds) {
        try {
            Assert.notEmpty(userIds);
            boolean isSuccess = userDao.activate(userIds) == userIds.size();
            if (isSuccess)
                return ResponseBuilder.success("启用成功！");
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error(className + "activate", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public ListResponse<User> getByOrgCode(String orgCode) {
        try {
            return ResponseBuilder.list(userDao.getByOrgCode(orgCode));
        } catch (Exception e) {
            log.error(className + "getByOrgCode", e);
            return ResponseBuilder.listFail(e.getMessage());
        }
    }

    @Override
    public ListResponse<User> findBySiteCode(String siteCode) {
        try {
            return ResponseBuilder.list(userDao.findBySiteCode(siteCode));
        } catch (Exception e) {
            log.error(className + "findBySiteCode", e);
            return ResponseBuilder.listFail(e.getMessage());
        }
    }
}
