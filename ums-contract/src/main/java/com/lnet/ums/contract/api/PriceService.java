package com.lnet.ums.contract.api;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerDto.PriceCalcDto;
import com.lnet.model.ums.customer.customerDto.PriceDto;
import com.lnet.model.ums.customer.customerDto.PriceRangeDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PriceService {
    /**
     * 新增
     * @param priceDto
     * @return
     */
    Response<PriceDto> create(PriceDto priceDto);

    /**
     * 批量新增
     * @param priceDtos
     * @return
     */
    Response<List<PriceDto>> batchCreate(List<PriceDto> priceDtos);

    /**
     * 修改
     * @param priceDto
     * @return
     */
    Response<PriceDto> update(PriceDto priceDto);

    /**
     * 查询通过Id
     * @param priceId
     * @return
     */
    Response<PriceDto> get(String priceId);

    /**
     * 分页查询
     * @param pageNumber
     * @param pageSize
     * @param params
     * @return
     */
    PageResponse<PriceDto> pageList(int pageNumber, int pageSize, Map<String, Object> params);

    /**
     * 匹配报价
     * @param dto
     * @return
     */
    ListResponse<PriceDto> findPrice(PriceDto dto);

    ListResponse<PriceRangeDto> findByPriceId(String priceId);

    /**
     *
     * @param priceId  价格ID
     * @param unit  区间值
     * @return
     */
    Response<PriceRangeDto> findByPriceId(String priceId, BigDecimal unit);

    /**
     *
     * @param ownerCode
     * @param calcAttr
     * @param transportType
     * @return
     */
    ListResponse<PriceDto> findByPrice(String ownerCode, String calcAttr, String transportType);


    /**
     * @param priceCalcDto 报价计算模型
     * @return Map<费用科目，价格>
     */
    public Response<Map<String, BigDecimal>> calculate(PriceCalcDto priceCalcDto);
}
