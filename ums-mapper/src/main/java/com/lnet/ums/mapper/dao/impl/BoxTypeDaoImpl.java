package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.customer.customerEntity.BoxType;
import com.lnet.ums.mapper.dao.BoxTypeDao;
import com.lnet.ums.mapper.dao.mappers.BoxTypeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class BoxTypeDaoImpl implements BoxTypeDao {

    @Resource
    BoxTypeMapper boxTypeMapper;
    @Override
    public Integer batchInsert(List<BoxType> boxType) {
        return boxTypeMapper.batchInsert(boxType);
    }

    @Override
    public Integer deleteByProjectCode(String projectCode) {
        return boxTypeMapper.deleteByProjectCode(projectCode);
    }

    @Override
    public List<BoxType> getByProjectCode(String projectCode) {
        return boxTypeMapper.getByProjectCode(projectCode);
    }
}
