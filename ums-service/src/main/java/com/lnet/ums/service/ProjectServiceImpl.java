package com.lnet.ums.service;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.ProjectService;
import com.lnet.model.ums.customer.customerEntity.BoxType;
import com.lnet.model.ums.customer.customerEntity.BusinessType;
import com.lnet.model.ums.customer.customerEntity.CargoType;
import com.lnet.model.ums.customer.customerEntity.Project;
import com.lnet.ums.mapper.dao.BoxTypeDao;
import com.lnet.ums.mapper.dao.BusinessTypeDao;
import com.lnet.ums.mapper.dao.CargoTypeDao;
import com.lnet.ums.mapper.dao.ProjectDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private final String className = this.getClass().getSimpleName() + ".";

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private BusinessTypeDao businessTypeDao;
    @Autowired
    private BoxTypeDao boxTypeDao;
    @Autowired
    private CargoTypeDao cargoTypeDao;

    @Override
    public Boolean exists(String code, String projectId) throws Exception {
        try {
            Assert.hasText(code);

            return projectDao.exists(code, projectId);
        } catch (Exception e) {
            log.error(className + "exists", e.getMessage());
            throw e;
        }
    }

    @Override
    public Response create(Project project) {
        try {
            Assert.notNull(project);
            Assert.hasText(project.getCode());

            Boolean isExists = this.exists(project.getCode(), null);
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            Boolean hasProject = projectDao.hasProject(project.getCustomerCode(), project.getBranchCode(), null);
            if (hasProject)
                return ResponseBuilder.fail("已有合作项目！");

            Assert.isTrue(project.getHeavyGoods() > project.getHeavyThrowGoods() && project.getHeavyThrowGoods() > project.getCommonGoods() && project.getCommonGoods() > project.getLightThrowGoods() && project.getLightThrowGoods() > project.getLightGoods(), "重货>重抛货>普通货>轻抛货>轻货");

            project.setProjectId(Snowflake.getInstance().next());
            project.setCreateTime(LocalDateTime.now());

            //完善业务类型，箱型，货物类型信息
            List<BusinessType> businessTypes = setBusinessType(project);
            List<BoxType> boxTypes = setBoxType(project);
            List<CargoType> cargoTypes = setCargoType(project);

            //插入业务类型，箱型，货物类型
            {
                if (null != businessTypes && 0 < businessTypes.size())
                    businessTypeDao.batchInsert(businessTypes);
                if (null != boxTypes && 0 < boxTypes.size())
                    boxTypeDao.batchInsert(boxTypes);
                if (null != cargoTypes && 0 < cargoTypes.size())
                    cargoTypeDao.batchInsert(cargoTypes);
            }

            //插入项目
            Boolean isSuccess = projectDao.insert(project) > 0;

            if (isSuccess)
                return ResponseBuilder.success("创建成功！");
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "create", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response update(Project project) {
        try {
            Assert.notNull(project);
            Assert.hasText(project.getCode());
            Assert.hasText(project.getProjectId());
            Assert.hasText(project.getBranchCode());
            Assert.hasText(project.getCustomerCode());

            Boolean isExists = this.exists(project.getCode(), project.getProjectId());
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            Boolean hasProject = projectDao.hasProject(project.getCustomerCode(), project.getBranchCode(), project.getProjectId());
            if (hasProject)
                return ResponseBuilder.fail("已有合作项目！");

            Assert.isTrue(project.getHeavyGoods() > project.getHeavyThrowGoods() && project.getHeavyThrowGoods() > project.getCommonGoods() && project.getCommonGoods() > project.getLightThrowGoods() && project.getLightThrowGoods() > project.getLightGoods(), "重货>重抛货>普通货>轻抛货>轻货");

            //完善业务类型，箱型，货物类型信息
            List<BusinessType> businessTypes = setBusinessType(project);
            List<BoxType> boxTypes = setBoxType(project);
            List<CargoType> cargoTypes = setCargoType(project);

            //删除原有的业务类型，箱型，货物类型信息
            {
                businessTypeDao.deleteByProjectCode(project.getCode());
                boxTypeDao.deleteByProjectCode(project.getCode());
                cargoTypeDao.deleteByProjectCode(project.getCode());
            }

            //插入业务类型，箱型，货物类型
            {
                if (null != businessTypes && 0 < businessTypes.size())
                    businessTypeDao.batchInsert(businessTypes);
                if (null != boxTypes && 0 < boxTypes.size())
                    boxTypeDao.batchInsert(boxTypes);
                if (null != cargoTypes && 0 < cargoTypes.size())
                    cargoTypeDao.batchInsert(cargoTypes);
            }

            project.setModifyTime(LocalDateTime.now());
            Boolean isSuccess = projectDao.update(project) > 0;
            if (isSuccess)
                return ResponseBuilder.success("修改成功！");
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(className + "update", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            Assert.hasText(code);

            Boolean isSuccess = projectDao.inactivate(code) > 0;
            if (isSuccess)
                return ResponseBuilder.success("禁用成功！");
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error(className + "inactivate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String code) {
        try {
            Assert.hasText(code);

            //条件检查
            {
                Project project = projectDao.getByCode(code);
                Assert.hasText(project.getCode(), "编码缺失,启用失败！");
                Assert.hasText(project.getName(), "项目名缺失,启用失败！");
                Assert.hasText(project.getCustomerCode(), "客户信息缺失,启用失败！");
                Assert.hasText(project.getBranchCode(), "分公司信息缺失,启用失败！");
                Assert.hasText(project.getSettleCycle().toString(), "结算周期信息缺失,启用失败！");
                Assert.hasText(project.getPaymentType().toString(), "支付方式信息缺失,启用失败！");
                Assert.hasText(project.getCalculateType().toString(), "计费方式信息缺失,启用失败！");
                Assert.hasText(project.getHandoverType().toString(), "交接方式信息缺失,启用失败！");
                Assert.hasText(project.getReceivableDataSource().toString(), "应收重量体积来源信息缺失,启用失败！");
                Assert.isTrue(project.getPackCoefficient() >= 1, "打包系数不小于1,启用失败！");
                Assert.isTrue(project.getLightGoods() >= 250, "轻货应不小于250,启用失败！");
                Assert.isTrue(project.getLightThrowGoods() > project.getLightGoods(), "轻抛货应大于轻货,启用失败！");
                Assert.isTrue(project.getCommonGoods() > project.getLightThrowGoods(), "普通货应大于轻抛货,启用失败！");
                Assert.isTrue(project.getHeavyThrowGoods() > project.getCommonGoods(), "重抛货应大于普通货,启用失败！");
                Assert.isTrue(project.getHeavyGoods() > project.getHeavyThrowGoods(), "重货应大于重抛货,“启用失败！");
            }

            Boolean isSuccess = projectDao.activate(code) > 0;
            if (isSuccess)
                return ResponseBuilder.success("启用成功！");
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error(className + "activate", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Project>> getAll() {
        try {
            List<Project> projects = projectDao.getAll();

            Assert.notEmpty(projects);
            return ResponseBuilder.success(projects);
        } catch (Exception e) {
            log.error(className + "getAll", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<Project>> getAvailable() {
        try {
            List<Project> projects = projectDao.getAvailable();

            Assert.notEmpty(projects, "获取数据失败！");
            return ResponseBuilder.success(projects);
        } catch (Exception e) {
            log.error(className + "getAvailable", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Project> get(String projectId) {
        try {
            Assert.hasText(projectId);

            Project project = projectDao.get(projectId);
            Assert.notNull(project, "获取数据失败！");

            //填充业务类型，箱型，货物类型
            {
                project.setBusinessTypes(businessTypeDao.getByProjectCode(project.getCode()));
                project.setBoxTypes(boxTypeDao.getByProjectCode(project.getCode()));
                project.setCargoTypes(cargoTypeDao.getByProjectCode(project.getCode()));
            }

            return ResponseBuilder.success(project);
        } catch (Exception e) {
            log.error(className + "get", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Project> getByCode(String code) {
        try {
            Assert.hasText(code);

            Optional<Project> projectOptional = Optional.ofNullable(projectDao.getByCode(code));
            if (projectOptional.isPresent()) {
                this.setProjectProperties(projectOptional.get());
                return ResponseBuilder.success(projectOptional.get(), "获取成功!");

            }
            return ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "getByCode", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<Project> getProject(String branchCode, String customerCode) {
        try {
            Assert.hasText(branchCode);
            Assert.hasText(customerCode);

            Optional<Project> projectOptional = Optional.ofNullable(projectDao.getProject(branchCode, customerCode));
            if (projectOptional.isPresent()) {
                this.setProjectProperties(projectOptional.get());
                return ResponseBuilder.success(projectOptional.get(), "获取成功!");

            }
            return ResponseBuilder.fail("获取失败!");
        } catch (Exception e) {
            log.error(className + "getProject", e.getMessage());
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<Project> pageList(Integer pageNo, Integer pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(pageNo);
            Assert.notNull(pageSize);

//            PageHelper.startPage(pageNo, pageSize);
//            PageInfo<Project> pageInfo = new PageInfo<>(projectDao.pageList(params));
            long total = projectDao.getAllCount(params);
            params.put("page", pageNo - 1);
            params.put("pageSize", pageSize);
            List<Project> list = projectDao.page(params);
            return ResponseBuilder.page(list, pageNo, pageSize, total);
        } catch (Exception e) {
            log.error(className + "pageList", e.getMessage());
            return (PageResponse) ResponseBuilder.fail(e);
        }
    }

    //完善业务类型
    private List<BusinessType> setBusinessType(Project project) {
        if (null == project.getBusinessTypes())
            return null;

        List<BusinessType> businessTypes = new ArrayList<>();
        project.getBusinessTypes().forEach(ele -> {
            ele.setBusinessTypeId(Snowflake.getInstance().next());
            ele.setProjectCode(project.getCode());
            businessTypes.add(ele);
        });

        return businessTypes;
    }

    //完善箱型
    private List<BoxType> setBoxType(Project project) {
        if (null == project.getBoxTypes())
            return null;

        List<BoxType> boxTypes = new ArrayList<>();
        project.getBoxTypes().forEach(ele -> {
            ele.setBoxTypeId(Snowflake.getInstance().next());
            ele.setProjectCode(project.getCode());
            boxTypes.add(ele);
        });

        return boxTypes;
    }

    //完善货物类型
    private List<CargoType> setCargoType(Project project) {
        if (null == project.getCargoTypes())
            return null;

        List<CargoType> cargoTypes = new ArrayList<>();
        project.getCargoTypes().forEach(ele -> {
            ele.setCargoTypeId(Snowflake.getInstance().next());
            ele.setProjectCode(project.getCode());
            cargoTypes.add(ele);
        });

        return cargoTypes;
    }

    //设置项目相关属性
    private Project setProjectProperties(Project project) {
        //填充业务类型，箱型，货物类型
        List<BusinessType> businessTypes = businessTypeDao.getByProjectCode(project.getCode());
        if (businessTypes != null && businessTypes.size() > 0) {
            project.setBusinessTypes(businessTypes);
        }
        List<BoxType> boxTypes = boxTypeDao.getByProjectCode(project.getCode());
        if (boxTypes != null && boxTypes.size() > 0) {
            project.setBoxTypes(boxTypes);
        }
        List<CargoType> cargoTypes = cargoTypeDao.getByProjectCode(project.getCode());
        if (cargoTypes != null && cargoTypes.size() > 0) {
            project.setCargoTypes(cargoTypes);
        }
        return project;
    }

    @Override
    public ListResponse<Project> getBranchAvailable(String branchCode) {
        try {
            Assert.hasText(branchCode);
            List<Project> projects = projectDao.getBranchAvailable(branchCode);
            return ResponseBuilder.list(projects);
        } catch (Exception e) {
            log.error(className + "getBranchAvailable", e.getMessage());
            return ResponseBuilder.listFail(e.getMessage());
        }
    }
}
