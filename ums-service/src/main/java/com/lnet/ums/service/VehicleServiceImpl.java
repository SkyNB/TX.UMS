package com.lnet.ums.service;

import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.VehicleService;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleCreateDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleListDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleUpdateDto;
import com.lnet.ums.mapper.dao.VehicleDao;
import com.lnet.ums.mapper.dao.VehicleTypeDao;
import com.lnet.model.ums.vehicle.vehicleEntity.Vehicle;
import com.lnet.model.ums.vehicle.vehicleEntity.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private VehicleTypeDao vehicleTypeDao;

    private List<VehicleType> vehicleTypes = new ArrayList<>();

    private void init() {
        vehicleTypes = vehicleTypeDao.findAll();
    }

    @Override
    public Response<String> create(VehicleCreateDto vehicleCreateDto) {
        try {
            Vehicle vehicle = Vehicle.builder()
                    .vehicleId(Snowflake.getInstance().next())
                    .vehicleTypeId(vehicleCreateDto.getVehicleTypeId())
                    .vehicleNo(vehicleCreateDto.getVehicleNo())
                    .driver(vehicleCreateDto.getDriver())
                    .driverMobile(vehicleCreateDto.getDriverMobile())
                    .leaseType(Vehicle.LeaseType.valueOf(vehicleCreateDto.getLeaseType().toString()))
                    .length(vehicleCreateDto.getLength())
                    .width(vehicleCreateDto.getWidth())
                    .height(vehicleCreateDto.getHeight())
                    .loadVolume(vehicleCreateDto.getLoadVolume())
                    .loadWeight(vehicleCreateDto.getLoadWeight())
                    .isActive(true)
                    .branchCode(vehicleCreateDto.getBranchCode())
                    .siteCode(vehicleCreateDto.getSiteCode())
                    .status(Vehicle.VehicleStatus.IDLE)
                    .identityCardNo(vehicleCreateDto.getIdentityCardNo())
                    .drivingLicence(vehicleCreateDto.getDrivingLicence())
                    .vehicleRegistractionNo(vehicleCreateDto.getVehicleRegistractionNo())
                    .emergencyContact(vehicleCreateDto.getEmergencyContact())
                    .emergencyContactMobile(vehicleCreateDto.getEmergencyContactMobile())
                    .contractEffectiveDate(vehicleCreateDto.getContractEffectiveDate())
                    .contractTerminationDate(vehicleCreateDto.getContractTerminationDate())
                    .buyTime(vehicleCreateDto.getBuyTime())
                    .province(vehicleCreateDto.getProvince())
                    .city(vehicleCreateDto.getCity())
                    .area(vehicleCreateDto.getArea())
                    .street(vehicleCreateDto.getStreet())
                    .address(vehicleCreateDto.getAddress())
                    .districtNo(vehicleCreateDto.getDistrictNo())
                    .build();

            /*Vehicle vehicle = BeanHelper.convert(vehicleCreateDto, Vehicle.class);
            vehicle.setVehicleId(Snowflake.getInstance().next());
            vehicle.setLeaseType(Vehicle.LeaseType.valueOf(vehicleCreateDto.getLeaseType().toString()));
            vehicle.setStatus(Vehicle.VehicleStatus.IDLE);*/
            if (vehicleCreateDto.getPaymentType() != null)
                vehicle.setPaymentType(Vehicle.PaymentType.valueOf(vehicleCreateDto.getPaymentType().toString()));

            //验证条件
            Response validate = validate(vehicle);
            if (!validate.isSuccess())
                return validate;

            boolean isExists = vehicleDao.exists(vehicle.getVehicleNo(), null);
            if (isExists)
                return ResponseBuilder.fail("车牌号已存在！");

            //开通账号
            /*Response<String> userResponse = createUser(vehicle);
            if (!userResponse.isSuccess())
                return userResponse;
            vehicle.setUserId(userResponse.getBody());*/
            vehicle.setUserId(vehicleCreateDto.getUserId());
            boolean isSuccess = vehicleDao.insert(vehicle) > 0;
            if (isSuccess)
                return ResponseBuilder.success(vehicle.getVehicleId());
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response update(VehicleUpdateDto vehicleUpdateDto) {
        try {
            Vehicle vehicle = Vehicle.builder()
                    .vehicleId(vehicleUpdateDto.getVehicleId())
                    .vehicleTypeId(vehicleUpdateDto.getVehicleTypeId())
                    .vehicleNo(vehicleUpdateDto.getVehicleNo())
                    .driver(vehicleUpdateDto.getDriver())
                    .driverMobile(vehicleUpdateDto.getDriverMobile())
                    .leaseType(Vehicle.LeaseType.valueOf(vehicleUpdateDto.getLeaseType().toString()))
                    .length(vehicleUpdateDto.getLength())
                    .width(vehicleUpdateDto.getWidth())
                    .height(vehicleUpdateDto.getHeight())
                    .loadVolume(vehicleUpdateDto.getLoadVolume())
                    .loadWeight(vehicleUpdateDto.getLoadWeight())
                    .userId(vehicleUpdateDto.getUserId())
                    .identityCardNo(vehicleUpdateDto.getIdentityCardNo())
                    .drivingLicence(vehicleUpdateDto.getDrivingLicence())
                    .vehicleRegistractionNo(vehicleUpdateDto.getVehicleRegistractionNo())
                    .emergencyContact(vehicleUpdateDto.getEmergencyContact())
                    .emergencyContactMobile(vehicleUpdateDto.getEmergencyContactMobile())
                    .contractEffectiveDate(vehicleUpdateDto.getContractEffectiveDate())
                    .contractTerminationDate(vehicleUpdateDto.getContractTerminationDate())
                    .buyTime(vehicleUpdateDto.getBuyTime())
                    .province(vehicleUpdateDto.getProvince())
                    .city(vehicleUpdateDto.getCity())
                    .area(vehicleUpdateDto.getArea())
                    .street(vehicleUpdateDto.getStreet())
                    .address(vehicleUpdateDto.getAddress())
                    .districtNo(vehicleUpdateDto.getDistrictNo())
                    .build();

            /*Vehicle vehicle = BeanHelper.convert(vehicleUpdateDto, Vehicle.class);
            vehicle.setLeaseType(Vehicle.LeaseType.valueOf(vehicleUpdateDto.getLeaseType().toString()));*/
            if (vehicleUpdateDto.getPaymentType() != null)
                vehicle.setPaymentType(Vehicle.PaymentType.valueOf(vehicleUpdateDto.getPaymentType().toString()));

            //验证条件
            Response validate = validate(vehicle);
            if (!validate.isSuccess())
                return validate;

            boolean isExists = vehicleDao.exists(vehicle.getVehicleNo(), vehicle.getVehicleId());
            if (isExists)
                return ResponseBuilder.fail("车牌号已存在！");

            //验证用户是否存在，不存在就重开账号
            /*User user = userService.get(vehicle.getUserId()).getBody();
            if (null == user) {
                Response<String> userResponse = createUser(vehicle);
                if (!userResponse.isSuccess())
                    return userResponse;
                vehicle.setUserId(userResponse.getBody());
            }*/

            //修改车辆信息
            boolean isSuccess = vehicleDao.update(vehicle) > 0;
            if (isSuccess)
                return ResponseBuilder.success();
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response batchInsert(List<VehicleCreateDto> vehicleDtos) {
        try{
            List<Vehicle> vehicles = new ArrayList<>();
            vehicleDtos.forEach(vehicleCreateDto->{
                Vehicle vehicle = Vehicle.builder()
                        .vehicleId(Snowflake.getInstance().next())
                        .vehicleTypeId(vehicleCreateDto.getVehicleTypeId())
                        .vehicleNo(vehicleCreateDto.getVehicleNo())
                        .driver(vehicleCreateDto.getDriver())
                        .driverMobile(vehicleCreateDto.getDriverMobile())
                        .leaseType(Vehicle.LeaseType.valueOf(vehicleCreateDto.getLeaseType().toString()))
                        .length(vehicleCreateDto.getLength())
                        .width(vehicleCreateDto.getWidth())
                        .height(vehicleCreateDto.getHeight())
                        .loadVolume(vehicleCreateDto.getLoadVolume())
                        .loadWeight(vehicleCreateDto.getLoadWeight())
                        .isActive(true)
                        .branchCode(vehicleCreateDto.getBranchCode())
                        .siteCode(vehicleCreateDto.getSiteCode())
                        .status(Vehicle.VehicleStatus.IDLE)
                        .identityCardNo(vehicleCreateDto.getIdentityCardNo())
                        .drivingLicence(vehicleCreateDto.getDrivingLicence())
                        .vehicleRegistractionNo(vehicleCreateDto.getVehicleRegistractionNo())
                        .emergencyContact(vehicleCreateDto.getEmergencyContact())
                        .emergencyContactMobile(vehicleCreateDto.getEmergencyContactMobile())
                        .contractEffectiveDate(vehicleCreateDto.getContractEffectiveDate())
                        .contractTerminationDate(vehicleCreateDto.getContractTerminationDate())
                        .buyTime(vehicleCreateDto.getBuyTime())
                        .province(vehicleCreateDto.getProvince())
                        .city(vehicleCreateDto.getCity())
                        .area(vehicleCreateDto.getArea())
                        .street(vehicleCreateDto.getStreet())
                        .address(vehicleCreateDto.getAddress())
                        .districtNo(vehicleCreateDto.getDistrictNo())
                        .userId(vehicleCreateDto.getUserId())
                        .build();
                if (vehicleCreateDto.getPaymentType() != null) {
                    vehicle.setPaymentType(Vehicle.PaymentType.valueOf(vehicleCreateDto.getPaymentType().toString()));
                }
                vehicles.add(vehicle);
            });
            Assert.isTrue(vehicleDao.batchInsert(vehicles) == vehicles.size());
            return ResponseBuilder.success();
        }catch (Exception e){
            log.error("", e);
            return  ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<VehicleListDto> get(String vehicleId) {
        try {
            Assert.hasText(vehicleId);
            init();
            Vehicle vehicle = vehicleDao.get(vehicleId);
            Assert.notNull(vehicle, "车辆信息获取失败！");

            VehicleListDto listDto = toVehicleListDto(vehicle);
            return ResponseBuilder.success(listDto);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<VehicleListDto> getByVehicleNo(String vehicleNo) {
        try {
            Assert.hasText(vehicleNo);
            init();
            Vehicle vehicle = vehicleDao.getByVehicleNo(vehicleNo);
            Assert.notNull(vehicle, "车辆信息获取失败！");

            VehicleListDto listDto = toVehicleListDto(vehicle);
            return ResponseBuilder.success(listDto);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleListDto>> getBranch(String branchCode) {
        try {
            Assert.hasText(branchCode);
            init();
            List<Vehicle> vehicles = vehicleDao.getByBranchCode(branchCode);

            List<VehicleListDto> listDtos = vehicles.stream().map(ele -> {
                return toVehicleListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleListDto>> getBranchAvailable(String branchCode) {
        try {
            Assert.hasText(branchCode);
            init();
            List<Vehicle> vehicles = vehicleDao.getActiveFromBranch(branchCode);

            List<VehicleListDto> listDtos = vehicles.stream().map(ele -> {
                return toVehicleListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleListDto>> getBranchIdle(String branchCode) {
        try {
            Assert.hasText(branchCode);
            init();
            List<Vehicle> vehicles = vehicleDao.getIdleFromBranch(branchCode, Vehicle.VehicleStatus.IDLE.toString());

            List<VehicleListDto> listDtos = vehicles.stream().map(ele -> {
                return toVehicleListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleListDto>> getSite(String branchCode, String siteCode) {
        try {
            Assert.hasText(branchCode, siteCode);
            init();
            List<Vehicle> vehicles = vehicleDao.getByBranchCodeAndSiteCode(branchCode, siteCode);

            List<VehicleListDto> listDtos = vehicles.stream().map(ele -> {
                return toVehicleListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleListDto>> getSiteAvailable(String branchCode, String siteCode) {
        try {
            Assert.hasText(branchCode);
            Assert.hasText(siteCode);
            init();
            List<Vehicle> vehicles = vehicleDao.getActiveFromSite(branchCode, siteCode);

            List<VehicleListDto> listDtos = vehicles.stream().map(ele -> {
                return toVehicleListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleListDto>> getSiteIdle(String branchCode, String siteCode) {
        try {
            Assert.hasText(branchCode);
            Assert.hasText(siteCode);
            init();
            List<Vehicle> vehicles = vehicleDao.getIdleFromSite(branchCode, siteCode, Vehicle.VehicleStatus.IDLE.toString());

            List<VehicleListDto> listDtos = vehicles.stream().map(ele -> {
                return toVehicleListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String vehicleNo) {
        try {
            Assert.hasText(vehicleNo);

            boolean isSuccess = vehicleDao.inactivate(vehicleNo) > 0;
            if (isSuccess)
                return ResponseBuilder.success();
            return ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String vehicleNo) {
        try {
            Assert.hasText(vehicleNo);

            boolean isSuccess = vehicleDao.activate(vehicleNo) > 0;
            if (isSuccess)
                return ResponseBuilder.success();
            return ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleListDto>> search(Map<String, Object> params) {
        try {
            init();
            return ResponseBuilder.supply(() -> {
                List<Vehicle> vehicles = vehicleDao.search(params);
                List<VehicleListDto> dtos = vehicles.stream().map(ele -> {
                    return toVehicleListDto(ele);
                }).collect(Collectors.toList());
                return dtos;
            });
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response changeStatusToIdle(String vehicleNo) {
        try {
            return vehicleDao.updateStatus(vehicleNo, Vehicle.VehicleStatus.IDLE.toString()) > 0 ? ResponseBuilder.success("", "重置车辆状态成功！") : ResponseBuilder.fail("重置车辆状态失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response changeStatusToBusy(String vehicleNo) {
        try {
            return vehicleDao.updateStatus(vehicleNo, Vehicle.VehicleStatus.BUSY.toString()) > 0 ? ResponseBuilder.success("", "重置车辆状态成功！") : ResponseBuilder.fail("重置车辆状态失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<VehicleListDto> getByUserId(String userId) {
        try {
            Assert.hasText(userId);
            init();
            Vehicle vehicle = vehicleDao.getByUserId(userId);
            return ResponseBuilder.success(toVehicleListDto(vehicle));
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Object importVehicle(InputStream inputStream, String currentBranchCode, String currentSiteCode) {
        return null;
    }

    private Response validate(Vehicle vehicle) {
        try {
            Assert.notNull(vehicle);
            Assert.hasText(vehicle.getVehicleId());
            Assert.hasText(vehicle.getVehicleNo());
            Assert.hasText(vehicle.getDriver());
            Assert.hasText(vehicle.getDriverMobile());
//            Assert.hasText(vehicle.getBranchCode());
//            Assert.hasText(vehicle.getSiteCode());

            //电话号码验证
            Pattern mobilePattern = Pattern.compile("1[345678]\\d-\\d{4}-\\d{4}");
            Matcher mobileMatcher = mobilePattern.matcher(vehicle.getDriverMobile());
            Assert.isTrue(mobileMatcher.matches(), "手机号码格式不正确！");

            //车牌号验证
            Pattern vehicleNoPattern = Pattern.compile("[\\u4e00-\\u9fa5][A-Z][A-Z_0-9]{5}");
            Matcher vehicleNoMatcher = vehicleNoPattern.matcher(vehicle.getVehicleNo());
            Assert.isTrue(vehicleNoMatcher.matches(), "车牌号格式不正确");

            return ResponseBuilder.success();
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    /*private Response<String> createUser(Vehicle vehicle) {
        try {
            User user = User.builder()
                    .username(vehicle.getDriverMobile().replaceAll("-", ""))
                    .password(vehicle.getDriverMobile().replaceAll("-", ""))
                    .fullName(vehicle.getDriver())
                    .orgCode(vehicle.getBranchCode())
                    .isActive(true)
                    .type(User.type.DRIVER)
                    .build();

            User createUser = userService.create(user).getBody();
            Assert.notNull(createUser, "创建用户失败！");

            //系统绑定
            Assert.isTrue(userService.bind(createUser.getUserId(), UserBinding.bindingType.SYSTEM, "TMS").isSuccess(), "用户系统绑定失败！");

            //组织架构绑定
            Assert.isTrue(userService.bind(createUser.getUserId(), UserBinding.bindingType.ORGANIZATION, vehicle.getBranchCode()).isSuccess(), "用户组织架构绑定失败！");

            return ResponseBuilder.success(createUser.getUserId());
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }
*/
    private VehicleListDto toVehicleListDto(Vehicle vehicle) {
        /*Optional<User> userOptional = users.stream().filter(ele -> ele.getUserId().equals(vehicle.getUserId())).findFirst();
        User user = new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }*/

        VehicleListDto listDto = VehicleListDto.builder()
                .vehicleId(vehicle.getVehicleId())
                .branchCode(vehicle.getBranchCode())
                .siteCode(vehicle.getSiteCode())
                //.username(user.getUserName())
                //.fullName(user.getFullName())
                .status(VehicleDto.VehicleStatus.valueOf(vehicle.getStatus().toString()))
                .build();

        listDto.setVehicleNo(vehicle.getVehicleNo());
        listDto.setDriver(vehicle.getDriver());
        listDto.setDriverMobile(vehicle.getDriverMobile());
        listDto.setLeaseType(VehicleDto.LeaseType.valueOf(vehicle.getLeaseType().toString()));
        listDto.setLength(vehicle.getLength());
        listDto.setWidth(vehicle.getWidth());
        listDto.setHeight(vehicle.getHeight());
        listDto.setLoadVolume(vehicle.getLoadVolume());
        listDto.setLoadWeight(vehicle.getLoadWeight());
        listDto.setIsActive(vehicle.getIsActive());
        listDto.setUserId(vehicle.getUserId());
        if (null != vehicle.getPaymentType())
            listDto.setPaymentType(VehicleDto.PaymentType.valueOf(vehicle.getPaymentType().toString()));
        listDto.setIdentityCardNo(vehicle.getIdentityCardNo());
        listDto.setDrivingLicence(vehicle.getDrivingLicence());
        listDto.setVehicleRegistractionNo(vehicle.getVehicleRegistractionNo());
        listDto.setEmergencyContact(vehicle.getEmergencyContact());
        listDto.setEmergencyContactMobile(vehicle.getEmergencyContactMobile());
        listDto.setContractEffectiveDate(vehicle.getContractEffectiveDate());
        listDto.setContractTerminationDate(vehicle.getContractTerminationDate());
        listDto.setBuyTime(vehicle.getBuyTime());
        listDto.setProvince(vehicle.getProvince());
        listDto.setCity(vehicle.getCity());
        listDto.setArea(vehicle.getArea());
        listDto.setStreet(vehicle.getStreet());
        listDto.setAddress(vehicle.getAddress());

        //填充车辆类型名称
        if (null != vehicle.getVehicleTypeId()) {
            Optional<VehicleType> vehicleTypeOptional = vehicleTypes.stream().filter(ele -> ele.getVehicleTypeId().equals(vehicle.getVehicleTypeId())).findFirst();
            VehicleType vehicleType = new VehicleType();
            if (vehicleTypeOptional.isPresent())
                vehicleType = vehicleTypeOptional.get();
            listDto.setVehicleTypeId(vehicle.getVehicleTypeId());
            listDto.setVehicleTypeName(vehicleType.getName());
        }

        //填充分公司名称
        /*Organization branch = new Organization();
        Optional<Organization> organizationOptional = branches.stream().filter(ele -> ele.getCode().equals(vehicle.getBranchCode())).findFirst();
        if (organizationOptional.isPresent())
            branch = organizationOptional.get();
        listDto.setBranchName(branch.getName());
*/
        //填充站点名称
        //if (siteService.getByCode(vehicle.getSiteCode()).isSuccess())
        /*Response<Site> siteResponse = siteService.getByCode(vehicle.getSiteCode());
        if (siteResponse.isSuccess())
            listDto.setSiteName(siteResponse.getBody().getName());*/

        return listDto;
    }
}
