package com.lnet.ums.contract.api;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleTypeDto;

import java.util.List;
import java.util.Map;

public interface VehicleTypeService {
    /**
     * 添加车辆类型
     *
     * @param vehicleTypeDto
     * @return
     */
    Response create(VehicleTypeDto vehicleTypeDto);

    /**
     * 修改车辆类型信息
     *
     * @param vehicleTypeDto
     * @return
     */
    Response update(VehicleTypeDto vehicleTypeDto);

    /**
     * 查询所有车辆类型
     *
     * @return
     */
    Response<List<VehicleTypeDto>> finAll();

    /**
     * 根据ID查询车辆信息
     *
     * @param vehicleTypeId
     * @return
     */
    Response<VehicleTypeDto> get(String vehicleTypeId);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    Response<List<VehicleTypeDto>> search(Map<String, Object> params);

    Response<List<VehicleTypeDto>> findAll();
}
