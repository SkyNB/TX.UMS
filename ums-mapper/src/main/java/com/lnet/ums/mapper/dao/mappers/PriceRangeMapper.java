package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerDto.PriceRangeDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Repository
public interface PriceRangeMapper {

    int batchCreate(List<PriceRangeDto> rangeDtos);

    int deleteByPriceId(String priceId);

    List<PriceRangeDto> findByPriceId(String priceId);

    PriceRangeDto getByPriceId(@Param("priceId") String priceId, @Param("unit") BigDecimal unit);
}
