package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerDto.PriceRangeDto;
import com.lnet.ums.mapper.dao.PriceRangeDao;
import com.lnet.ums.mapper.dao.mappers.PriceRangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Repository
public class PriceRangeDaoImpl implements PriceRangeDao {

    @Autowired
    PriceRangeMapper priceRangeMapper;

    @Override
    public int batchCreate(List<PriceRangeDto> rangeDtos) {
        return priceRangeMapper.batchCreate(rangeDtos);
    }

    @Override
    public int deleteByPriceId(String priceId) {
        return priceRangeMapper.deleteByPriceId(priceId);
    }

    @Override
    public List<PriceRangeDto> findByPriceId(String priceId) {
        return priceRangeMapper.findByPriceId(priceId);
    }

    @Override
    public PriceRangeDto findByPriceId(String priceId, BigDecimal unit) {
        return priceRangeMapper.getByPriceId(priceId, unit);
    }
}
