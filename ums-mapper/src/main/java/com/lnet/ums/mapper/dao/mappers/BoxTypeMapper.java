package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.customer.customerEntity.BoxType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxTypeMapper {
    Integer batchInsert(List<BoxType> boxType);

    Integer deleteByProjectCode(String projectCode);

    List<BoxType> getByProjectCode(String projectCode);
}
