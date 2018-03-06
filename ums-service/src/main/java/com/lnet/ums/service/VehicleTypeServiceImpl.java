package com.lnet.ums.service;

import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.BeanHelper;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.VehicleTypeService;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleTypeDto;
import com.lnet.ums.mapper.dao.VehicleTypeDao;
import com.lnet.model.ums.vehicle.vehicleEntity.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class VehicleTypeServiceImpl implements VehicleTypeService {
    @Autowired
    private VehicleTypeDao vehicleTypeDao;

    @Override
    public Response create(VehicleTypeDto vehicleTypeDto) {
        try {
            Response validate = validate(vehicleTypeDto);
            if (!validate.isSuccess())
                return validate;

            boolean isExists = vehicleTypeDao.exists(null, vehicleTypeDto.getName());
            Assert.isTrue(!isExists, "车辆类型名称已存在！");

            VehicleType vehicleType = VehicleType.builder()
                    .vehicleTypeId(Snowflake.getInstance().next())
                    .name(vehicleTypeDto.getName())
                    .length(vehicleTypeDto.getLength())
                    .width(vehicleTypeDto.getWidth())
                    .height(vehicleTypeDto.getHeight())
                    .loadVolume(vehicleTypeDto.getLoadVolume())
                    .loadWeight(vehicleTypeDto.getLoadWeight())
                    .build();

            boolean isSuccess = vehicleTypeDao.insert(vehicleType) > 0;
            if (isSuccess)
                return ResponseBuilder.success();
            return ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response update(VehicleTypeDto vehicleTypeDto) {
        try {
            Response validate = validate(vehicleTypeDto);
            if (!validate.isSuccess())
                return validate;

            boolean isExists = vehicleTypeDao.exists(vehicleTypeDto.getVehicleTypeId(), vehicleTypeDto.getName());
            Assert.isTrue(!isExists, "车辆类型名称已存在！");

            VehicleType vehicleType = VehicleType.builder()
                    .vehicleTypeId(vehicleTypeDto.getVehicleTypeId())
                    .name(vehicleTypeDto.getName())
                    .length(vehicleTypeDto.getLength())
                    .width(vehicleTypeDto.getWidth())
                    .height(vehicleTypeDto.getHeight())
                    .loadVolume(vehicleTypeDto.getLoadVolume())
                    .loadWeight(vehicleTypeDto.getLoadWeight())
                    .build();

            boolean isSuccess = vehicleTypeDao.update(vehicleType) > 0;
            if (isSuccess)
                return ResponseBuilder.success();
            return ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleTypeDto>> finAll() {
        try {
            List<VehicleType> vehicleTypes = vehicleTypeDao.findAll();

            List<VehicleTypeDto> dtos = vehicleTypes.stream()
                    .map(ele -> {
                        return VehicleTypeDto.builder()
                                .vehicleTypeId(ele.getVehicleTypeId())
                                .name(ele.getName())
                                .length(ele.getLength())
                                .width(ele.getWidth())
                                .height(ele.getHeight())
                                .loadVolume(ele.getLoadVolume())
                                .loadWeight(ele.getLoadWeight())
                                .build();
                    }).collect(Collectors.toList());

            return ResponseBuilder.success(dtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<VehicleTypeDto> get(String vehicleTypeId) {
        try {
            Assert.hasText(vehicleTypeId);

            VehicleType vehicleType = vehicleTypeDao.get(vehicleTypeId);
            Assert.notNull(vehicleType, "车辆类型信息获取失败！");

            VehicleTypeDto dto = VehicleTypeDto.builder()
                    .vehicleTypeId(vehicleType.getVehicleTypeId())
                    .name(vehicleType.getName())
                    .length(vehicleType.getLength())
                    .width(vehicleType.getWidth())
                    .height(vehicleType.getHeight())
                    .loadVolume(vehicleType.getLoadVolume())
                    .loadWeight(vehicleType.getLoadWeight())
                    .build();

            return ResponseBuilder.success(dto);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleTypeDto>> search(Map<String, Object> params) {
        try {
            return ResponseBuilder.supply(() -> {
                List<VehicleType> vehicleTypes = vehicleTypeDao.search(params);
                List<VehicleTypeDto> dtos = vehicleTypes.stream().map(ele -> {
                    return VehicleTypeDto.builder()
                            .vehicleTypeId(ele.getVehicleTypeId())
                            .name(ele.getName())
                            .length(ele.getLength())
                            .width(ele.getWidth())
                            .height(ele.getHeight())
                            .loadVolume(ele.getLoadVolume())
                            .loadWeight(ele.getLoadWeight())
                            .build();
                }).collect(Collectors.toList());
                return dtos;
            });
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<VehicleTypeDto>> findAll() {
        try {

            List<VehicleType> list = vehicleTypeDao.findAll();

            return ResponseBuilder.list(BeanHelper.convertList(list, VehicleTypeDto.class));
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    private Response validate(VehicleTypeDto vehicleTypeDto) {
        try {
            Assert.notNull(vehicleTypeDto, "参数不能为空！");
            Assert.hasText(vehicleTypeDto.getName(), "名称不能为空！");
            Assert.notNull(vehicleTypeDto.getLength(), "长度不能为空！");
            Assert.notNull(vehicleTypeDto.getWidth(), "宽度不能为空！");
            Assert.notNull(vehicleTypeDto.getHeight(), "高度不能为空！");

            return ResponseBuilder.success();
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}
