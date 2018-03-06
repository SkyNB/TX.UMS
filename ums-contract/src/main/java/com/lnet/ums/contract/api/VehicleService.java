package com.lnet.ums.contract.api;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleCreateDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleListDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleUpdateDto;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface VehicleService {
    /**
     * 增加车辆信息
     *
     * @param vehicleCreateDto
     * @return
     */
    Response<String> create(VehicleCreateDto vehicleCreateDto);

    /**
     * 修改车辆信息
     *
     * @param vehicleUpdateDto
     * @return
     */
    Response update(VehicleUpdateDto vehicleUpdateDto);

    /**
     * 批量添加车辆信息(车牌号相同的进行修改)
     *
     * @param vehicleDtos
     * @return
     */
    Response batchInsert(List<VehicleCreateDto> vehicleDtos);

    /**
     * 根据ID获取车辆信息
     *
     * @param vehicleId
     * @return
     */
    Response<VehicleListDto> get(String vehicleId);

    /**
     * 根据车牌号获取车辆信息
     *
     * @param vehicleNo
     * @return
     */
    Response<VehicleListDto> getByVehicleNo(String vehicleNo);

    /**
     * 获取分公司所有车辆信息
     *
     * @param branchCode
     * @return
     */
    Response<List<VehicleListDto>> getBranch(String branchCode);

    /**
     * 获取分公司可用的车辆信息
     *
     * @param branchCode
     * @return
     */
    Response<List<VehicleListDto>> getBranchAvailable(String branchCode);

    /**
     * 获取分公司空闲的车辆信息
     *
     * @param branchCode
     * @return
     */
    Response<List<VehicleListDto>> getBranchIdle(String branchCode);

    /**
     * 获取站点所有车辆信息
     *
     * @param branchCode
     * @param siteCode
     * @return
     */
    Response<List<VehicleListDto>> getSite(String branchCode, String siteCode);

    /**
     * 获取站点可用的车辆信息
     *
     * @param branchCode
     * @param siteCode
     * @return
     */
    Response<List<VehicleListDto>> getSiteAvailable(String branchCode, String siteCode);

    /**
     * 获取站点的空闲车辆信息
     *
     * @param branchCode
     * @param siteCode
     * @return
     */
    Response<List<VehicleListDto>> getSiteIdle(String branchCode, String siteCode);

    /**
     * 禁用车辆
     *
     * @param vehicleNo
     * @return
     */
    Response inactivate(String vehicleNo);

    /**
     * 启用车辆
     *
     * @param vehicleNo
     * @return
     */
    Response activate(String vehicleNo);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    Response<List<VehicleListDto>> search(Map<String, Object> params);

    /**
     * 将车辆状态置为空闲状态
     *
     * @param vehicleNo
     * @return
     */
    Response changeStatusToIdle(String vehicleNo);

    /**
     * 将车辆状态置为忙碌状态
     *
     * @param vehicleNo
     * @return
     */
    Response changeStatusToBusy(String vehicleNo);

    /**
     * 通过用户ID查询车辆
     * @param userId
     * @return
     */
    Response<VehicleListDto> getByUserId(String userId);

    Object importVehicle(InputStream inputStream, String currentBranchCode, String currentSiteCode);
}
