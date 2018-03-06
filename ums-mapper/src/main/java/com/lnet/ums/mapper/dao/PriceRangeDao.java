package com.lnet.ums.mapper.dao;


import com.lnet.model.ums.customer.customerDto.PriceRangeDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface PriceRangeDao {
    int batchCreate(List<PriceRangeDto> rangeDtos);

    int deleteByPriceId(String priceId);

    List<PriceRangeDto> findByPriceId(String priceId);

    PriceRangeDto findByPriceId(String priceId, BigDecimal unit);

}
