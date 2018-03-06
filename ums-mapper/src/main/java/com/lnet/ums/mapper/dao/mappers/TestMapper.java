package com.lnet.ums.mapper.dao.mappers;


import com.lnet.ums.models.TestModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by LH on 2017/1/19.
 */
@Repository
public interface TestMapper {
    boolean exists(@Param("vehicleTypeId") String vehicleTypeId, @Param("name") String name);

    int insert(TestModel vehicleType);
}
