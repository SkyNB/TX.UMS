package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.customer.customerEntity.BoxType;

import java.util.List;

public interface BoxTypeDao {
    Integer batchInsert(List<BoxType> boxType);

    Integer deleteByProjectCode(String projectCode);

    List<BoxType> getByProjectCode(String projectCode);
}
