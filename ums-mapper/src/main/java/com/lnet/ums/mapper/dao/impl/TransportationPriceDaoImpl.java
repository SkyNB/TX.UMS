package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerDto.PriceDto;
import com.lnet.ums.mapper.dao.TransportationPriceDao;
import com.lnet.ums.mapper.dao.mappers.TransportationPriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6.
 */
@Repository
public class TransportationPriceDaoImpl implements TransportationPriceDao {

    @Autowired
    TransportationPriceMapper priceMapper;

    @Override
    public int create(PriceDto priceDto) {
        return priceMapper.create(priceDto);
    }

    @Override
    public int batchCreate(List<PriceDto> priceDtos) {
        return priceMapper.batchCreate(priceDtos);
    }

    @Override
    public int update(PriceDto priceDto) {
        return priceMapper.update(priceDto);
    }

    @Override
    public PriceDto get(String priceId) {
        return priceMapper.get(priceId);
    }

    @Override
    public List<PriceDto> search(Map<String, Object> params) {
        return priceMapper.search(params);
    }

    @Override
    public List<PriceDto> findPrice(PriceDto dto) {
        return priceMapper.findPrice(dto);
    }

    @Override
    public List<PriceDto> findByPrice(String ownerCode, String calcAttr, String transportType) {
        return priceMapper.findByPrice(ownerCode, calcAttr, transportType);
    }
}
