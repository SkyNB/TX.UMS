package com.lnet.ums.mapper.dao;


import com.lnet.model.ums.customer.customerDto.PriceDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface TransportationPriceDao {
    int create(PriceDto priceDto);

    int batchCreate(List<PriceDto> priceDtos);

    int update(PriceDto priceDto);

    PriceDto get(String priceId);

    List<PriceDto> search(Map<String, Object> params);

    List<PriceDto> findPrice(PriceDto dto);

    List<PriceDto> findByPrice(String ownerCode, String calcAttr, String transportType);
}
