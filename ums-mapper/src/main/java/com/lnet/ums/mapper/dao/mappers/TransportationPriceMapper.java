package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerDto.PriceDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6.
 */

@Repository
public interface TransportationPriceMapper {
    int create(PriceDto priceDto);

    int batchCreate(List<PriceDto> priceDtos);

    int update(PriceDto priceDto);

    PriceDto get(String priceId);

    List<PriceDto> search(Map<String, Object> params);

    List<PriceDto> findPrice(PriceDto dto);

    List<PriceDto> findByPrice(@Param("ownerCode") String ownerCode,
                               @Param("calcAttr") String calcAttr, @Param("transportType") String transportType);
}
