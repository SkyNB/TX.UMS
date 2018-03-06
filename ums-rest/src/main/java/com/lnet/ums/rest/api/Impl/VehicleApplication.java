package com.lnet.ums.rest.api.Impl;

import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.excel.reader.ExcelReader;
import com.lnet.framework.excel.util.ExcelFormat;
import com.lnet.model.ums.organization.Organization;
import com.lnet.model.ums.site.Site;
import com.lnet.model.ums.user.User;
import com.lnet.model.ums.user.UserBinding;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleCreateDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleListDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleUpdateDto;
import com.lnet.ums.contract.api.OrganizationService;
import com.lnet.ums.contract.api.SiteService;
import com.lnet.ums.contract.api.UserService;
import com.lnet.ums.contract.api.VehicleService;
import com.lnet.ums.rest.api.uitls.SystemUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class VehicleApplication {
    @Resource
    private VehicleService vehicleService;
    @Resource
    private UserService userService;
    @Resource
    private OrganizationService organizationService;
    @Resource
    private SiteService siteService;

    public Response create(VehicleCreateDto vehicleCreateDto) {
        //创建用户
        User user = User.builder()
                .username(vehicleCreateDto.getDriverMobile().replaceAll("-", ""))
                .password(vehicleCreateDto.getDriverMobile().replaceAll("-", ""))
                .fullName(vehicleCreateDto.getDriver())
                .orgCode(vehicleCreateDto.getBranchCode())
                .siteCode(vehicleCreateDto.getSiteCode())
                .isActive(true)
                .type(User.type.DRIVER)
                .build();

        Response<User> response = userService.create(user);
        if (!response.isSuccess())
            return response;

        //绑定系统，组织，站点
        Response resp = userService.bind(response.getBody().getUserId(), UserBinding.bindingType.SYSTEM, SystemUtil.SYS_CODE);
        Response resp2 = userService.bind(response.getBody().getUserId(), UserBinding.bindingType.ORGANIZATION, vehicleCreateDto.getBranchCode());
        Response resp3 = userService.bind(response.getBody().getUserId(), UserBinding.bindingType.SITE, vehicleCreateDto.getSiteCode());
        if (!(resp.isSuccess() && resp2.isSuccess() && resp3.isSuccess()))
            return ResponseBuilder.fail("创建失败！");

        vehicleCreateDto.setUserId(response.getBody().getUserId());
        return vehicleService.create(vehicleCreateDto);
    }

    public Response update(VehicleUpdateDto vehicleUpdateDto) {
        return vehicleService.update(vehicleUpdateDto);
    }

    public Response<VehicleListDto> get(String vehicleId) {
        Response<VehicleListDto> response = vehicleService.get(vehicleId);
        if (response.getBody() != null) {
            perfectDto(response.getBody());
        }
        return response;
    }

    public Response inactivate(String vehicleNo) {
        return vehicleService.inactivate(vehicleNo);
    }

    public Response activate(String vehicleNo) {
        return vehicleService.activate(vehicleNo);
    }

    public Response<VehicleListDto> getByVehicleNo(String vehicleNo) {
        Response<VehicleListDto> response = vehicleService.getByVehicleNo(vehicleNo);
        if (response.getBody() != null) {
            perfectDto(response.getBody());
        }
        return response;
    }

    public Response<List<VehicleListDto>> getBranch(String branchCode) {
        Response<List<VehicleListDto>> response = vehicleService.getBranch(branchCode);
        response.getBody().forEach(f -> perfectDto(f));
        return response;
    }

    public Response<List<VehicleListDto>> getBranchAvailable(String branchCode) {
        Response<List<VehicleListDto>> response = vehicleService.getBranchAvailable(branchCode);
        response.getBody().forEach(f -> perfectDto(f));
        return response;
    }

    public Response<List<VehicleListDto>> getBranchIdle(String branchCode) {
        Response<List<VehicleListDto>> response = vehicleService.getBranchIdle(branchCode);
        response.getBody().forEach(f -> perfectDto(f));
        return response;
    }

    public Response<List<VehicleListDto>> getSite(String branchCode, String siteCode) {
        Response<List<VehicleListDto>> response = vehicleService.getSite(branchCode, siteCode);
        response.getBody().forEach(f -> perfectDto(f));
        return response;
    }

    public Response<List<VehicleListDto>> getSiteAvailable(String branchCode, String siteCode) {
        Response<List<VehicleListDto>> response = vehicleService.getSiteAvailable(branchCode, siteCode);
        if (null != response.getBody())
            response.getBody().forEach(f -> perfectDto(f));
        return response;
    }

    public Response<List<VehicleListDto>> getSiteIdle(String branchCode, String siteCode) {
        Response<List<VehicleListDto>> response = vehicleService.getSiteIdle(branchCode, siteCode);
        response.getBody().forEach(f -> perfectDto(f));
        return response;
    }

    public Response<List<VehicleListDto>> search(Map<String, Object> params) {
        Response<List<VehicleListDto>> response = vehicleService.search(params);
        response.getBody().forEach(f -> {
            perfectDto(f);
        });
        return response;
    }

    private VehicleListDto perfectDto(VehicleListDto vehicleListDto) {
        //填充用户信息
        /*Optional<User> userOptional = users.stream().filter(f -> f.getUserId().equals(vehicleListDto.getUserId())).findFirst();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            vehicleListDto.setUsername(user.getUsername());
            vehicleListDto.setFullName(user.getFullName());
        }*/
        Response<User> userResponse = userService.get(vehicleListDto.getUserId());
        User user = userResponse.getBody();
        if (user != null) {
            vehicleListDto.setUsername(user.getUsername());
            vehicleListDto.setFullName(user.getFullName());
        }

        //填充分公司信息
        /*Optional<Organization> branchOptional = branches.stream().filter(f -> f.getCode().equals(vehicleListDto.getBranchCode())).findFirst();
        if (branchOptional.isPresent()) {
            Organization branch = branchOptional.get();
            vehicleListDto.setBranchName(branch.getName());
        }*/
        Response<Organization> organizationResponse = organizationService.getByCode(vehicleListDto.getBranchCode());
        Organization branch = organizationResponse.getBody();
        if (branch != null)
            vehicleListDto.setBranchName(branch.getName());

        //填充站点信息
        Response<Site> siteResponse = siteService.getByCode(vehicleListDto.getSiteCode());
        if (siteResponse.isSuccess()) {
            if (null != siteResponse.getBody())
                vehicleListDto.setSiteName(siteResponse.getBody().getName());
        }

        return vehicleListDto;
    }

    public Response<VehicleListDto> getByUserId(String userId) {
        return vehicleService.getByUserId(userId);
    }

    public Response<List<Response>> importVehicle(InputStream inputStream, String branchCode, String siteCode) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            List<Response> result = new ArrayList<>();
            List<VehicleListDto> vehicleDtos = vehicleService.getSite(branchCode, siteCode).getBody();
            if (vehicleDtos == null) {
                return ResponseBuilder.fail("获取系统已有车辆信息失败");
            }
            Set<String> vehicleNos = new HashSet<String>();
            Set<String> mobiles = new HashSet<String>();
            List<VehicleCreateDto> vehicleCreateDtos = ExcelReader.readByColumnName(inputStream, ExcelFormat.OFFICE2007, 0, 0, row -> {
                Response response = ResponseBuilder.success(null, "");
                result.add(response);
                VehicleCreateDto vehicleCreateDto = new VehicleCreateDto();
                vehicleCreateDto.setBranchCode(branchCode);
                vehicleCreateDto.setSiteCode(siteCode);

                String driver = (String) row.getColumnValue("司机");
                if (driver == null || driver.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "司机为空");
                    return null;
                }
                vehicleCreateDto.setDriver(driver);

                String driverMobile = (String) row.getColumnValue("司机电话");
                if (driverMobile == null || driverMobile.length() != 11) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "司机电话为空或者格式错误");
                    return null;
                } else if (mobiles.contains(driverMobile)) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "导入的数据中有重复的司机电话号码");
                    return null;
                }
                vehicleCreateDto.setDriverMobile(driverMobile);

                String vehicleNo = (String) row.getColumnValue("车牌号");
                if (vehicleNo == null || vehicleNo.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "车牌号为空");
                    return null;
                } else if (existsVehicleNo(vehicleDtos, vehicleNo)) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "车牌号已存在");
                    return null;
                } else if (vehicleNos.contains(vehicleNo)) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "导入的数据中有重复的车牌号");
                    return null;
                }
                vehicleCreateDto.setVehicleNo(vehicleNo);

                String identityCardNo = (String) row.getColumnValue("身份证");
                if (identityCardNo == null || identityCardNo.length() != 18) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "身份证为空或者格式错误");
                    return null;
                }
                vehicleCreateDto.setIdentityCardNo(identityCardNo);

                String drivingLicence = (String) row.getColumnValue("驾驶证");
                if (drivingLicence == null || drivingLicence.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "驾驶证为空");
                    return null;
                }
                vehicleCreateDto.setDrivingLicence(drivingLicence);

                String vehicleRegistractionNo = (String) row.getColumnValue("行驶证");
                if (vehicleRegistractionNo == null || vehicleRegistractionNo.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "行驶证为空");
                    return null;
                }
                vehicleCreateDto.setVehicleRegistractionNo(vehicleRegistractionNo);

                String buyTime = (String) row.getColumnValue("购车日期");
                try {
                    vehicleCreateDto.setBuyTime(LocalDate.parse((String) buyTime, formatter));
                } catch (Exception e) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "购车日期为空或者格式错误");
                    return null;
                }

                String contractEffectiveDate = (String) row.getColumnValue("合同开始日期");
                try {
                    vehicleCreateDto.setContractEffectiveDate(LocalDate.parse((String) contractEffectiveDate, formatter));
                } catch (Exception e) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "合同开始日期为空或者格式错误");
                    return null;
                }

                String contractTerminationDate = (String) row.getColumnValue("合同到期日期");
                try {
                    vehicleCreateDto.setContractTerminationDate(LocalDate.parse((String) contractTerminationDate, formatter));
                } catch (Exception e) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "合同到期日期为空或者格式错误");
                    return null;
                }

                VehicleDto.LeaseType leaseType = getLeaseType((String) row.getColumnValue("租赁方式"));
                if (leaseType != null) {
                    vehicleCreateDto.setLeaseType(leaseType);
                } else {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "租赁方式为空或者填写有误");
                    return null;
                }

                VehicleDto.PaymentType paymentType = getPaymentType((String) row.getColumnValue("结算方式"));
                if (paymentType != null) {
                    vehicleCreateDto.setPaymentType(paymentType);
                } else {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "结算方式为空或者填写有误");
                    return null;
                }

                String emergencyContact = (String) row.getColumnValue("紧急联系人");
                if (emergencyContact == null || emergencyContact.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "紧急联系人为空");
                    return null;
                }
                vehicleCreateDto.setEmergencyContact(emergencyContact);

                String emergencyContactMobile = (String) row.getColumnValue("紧急联系人电话");
                if (emergencyContactMobile == null || emergencyContactMobile.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "紧急联系人电话为空或者格式错误");
                    return null;
                }
                vehicleCreateDto.setEmergencyContactMobile(emergencyContactMobile);

                Boolean getSizeSuccess = false;
                String vehicleSize = (String) row.getColumnValue("车厢尺寸");
                if (vehicleSize != null) {
                    String size[] = vehicleSize.split("\\*");
                    if (size.length == 3) {
                        try {
                            Double length = Double.parseDouble(size[0]);
                            Double width = Double.parseDouble(size[1]);
                            Double height = Double.parseDouble(size[2]);
                            if (length > 0 && width > 0 && height > 0) {
                                vehicleCreateDto.setLength(length);
                                vehicleCreateDto.setWidth(width);
                                vehicleCreateDto.setHeight(height);
                                vehicleCreateDto.setLoadVolume(height * width * height);
                                getSizeSuccess = true;
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                if (!getSizeSuccess) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "车厢尺寸为空、为零或者格式错误");
                    return null;
                }

                boolean getLoadWeightSuccess = false;
                String loadWeight = (String) row.getColumnValue("吨位");
                if (loadWeight != null) {
                    try {
                        Double weight = Double.parseDouble(loadWeight);
                        if (weight > 0) {
                            vehicleCreateDto.setLoadWeight(weight);
                            getLoadWeightSuccess = true;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
                if (!getLoadWeightSuccess) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "吨位为空、为零或者格式错误");
                    return null;
                }

                response.setMessage("创建用户：" + driverMobile);
                vehicleNos.add(vehicleNo);
                mobiles.add(driverMobile);

                return vehicleCreateDto;
            });

            vehicleCreateDtos.removeIf(dto -> null == dto);

            if (vehicleCreateDtos.size() > 0) {
                Map<String, Response<User>> userResponseMap = new HashMap<>();
                vehicleCreateDtos.forEach(dto -> {
                    //创建用户
                    User user = User.builder()
                            .username(dto.getDriverMobile())
                            .password(dto.getDriverMobile())
                            .fullName(dto.getDriver())
                            .orgCode(dto.getBranchCode())
                            .siteCode(dto.getSiteCode())
                            .isActive(true)
                            .type(User.type.DRIVER)
                            .build();
                    userResponseMap.put(dto.getDriverMobile(), userService.create(user));
                });
                vehicleCreateDtos.removeIf(dto -> !userResponseMap.get(dto.getDriverMobile()).isSuccess());
                //为创建用户成功的设置Id
                vehicleCreateDtos.forEach(dto -> {
                    dto.setUserId(userResponseMap.get(dto.getDriverMobile()).getBody().getUserId());
                });
                //添加创建用户失败的记录
                for (Map.Entry<String, Response<User>> entry : userResponseMap.entrySet()) {
                    if (!entry.getValue().isSuccess()) {
                        for (Response response : result) {
                            if (response.isSuccess()) {
                                String message = "创建用户：" + entry.getKey();
                                if (message.equals(response.getMessage())) {
                                    response.setMessage("创建用户失败，请检查用户" + entry.getKey() + "是否已存在");
                                    response.setSuccess(false);
                                    break;
                                }
                            }
                        }
                    }
                }

                Response createResponse = vehicleService.batchInsert(vehicleCreateDtos);
                if (createResponse.isSuccess()) {
                    createResponse.setMessage("成功导入" + vehicleCreateDtos.size() + "条数据");
                    return createResponse.setBody(result);
                }
            }
            Response response = ResponseBuilder.success(result);
            if (!result.isEmpty()) {
                response.setSuccess(false);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.fail(e);
        }
    }

    private VehicleDto.LeaseType getLeaseType(String text) {
        for (VehicleDto.LeaseType item : VehicleDto.LeaseType.values()) {
            if (text != null && text.equals(item.getText())) {
                return item;
            }
        }
        return null;
    }

    private VehicleDto.PaymentType getPaymentType(String text) {
        for (VehicleDto.PaymentType item : VehicleDto.PaymentType.values()) {
            if (text != null && text.equals(item.getText())) {
                return item;
            }
        }
        return null;
    }

    //根据车牌号 检查车辆是否已存在
    private boolean existsVehicleNo(List<VehicleListDto> vehicleDtos, String vehicleNo) {
        for (VehicleListDto dto : vehicleDtos) {
            if (dto.getVehicleNo().equals(vehicleNo)) {
                return true;
            }
        }
        return false;
    }
}
