package com.lnet.ums.contract.api;


import com.lnet.framework.core.Response;
import com.lnet.model.ums.carrier.carrierDto.CarrierCreateDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierListDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierUpdateDto;

import java.util.List;
import java.util.Map;

public interface CarrierService {
    /**
     * 新建承运商
     *
     * @param carrierCreateDto
     * @return
     */
    Response<String> create(CarrierCreateDto carrierCreateDto);

    Response<List<Response>> batchCreate(List<CarrierCreateDto> carrierCreateDtos);

    /**
     * 修改承运商信息
     *
     * @param carrierUpdateDto
     * @return
     */
    Response<String> update(CarrierUpdateDto carrierUpdateDto);

    /**
     * 获取所有的承运商信息
     *
     * @return
     */
    Response<List<CarrierListDto>> getAll();

    /**
     * 根据ID获取承运商信息
     *
     * @param carrierId
     * @return
     */
    Response<CarrierListDto> get(String carrierId);

    /**
     * 通过code获取承运商信息
     *
     * @param code
     * @return
     */
    Response<CarrierListDto> getByCode(String code);

    /**
     * 获取分公司所有的承运商信息
     *
     * @param branchCode
     * @return
     */
    Response<List<CarrierListDto>> getBranch(String branchCode);

    /**
     * 获取分公司可用的承运商信息
     *
     * @param branchCode
     * @return
     */
    Response<List<CarrierListDto>> getBranchAvailable(String branchCode);

    /**
     * 禁用
     *
     * @param code
     * @return
     */
    Response inactivate(String code);

    /**
     * 启用
     *
     * @param code
     * @return
     */
    Response activate(String code);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    Response<List<CarrierListDto>> search(Map<String, Object> params);
}
