package com.lnet.ums.mapper.dao.mappers;

import com.lnet.model.ums.user.UserBinding;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBindingMapper {

    boolean exists(@Param("userId") String userId, @Param("bindingType") String bindingType);

    int update(@Param("userId") String userId, @Param("bindingValue") String bindingValue);

    int bind(UserBinding userBinding);

    int unBind(@Param("userId") String userId, @Param("bindingType") String bindingType);

    List<UserBinding> getBindings(String userId);

    UserBinding getBinding(@Param("userId") String userId, @Param("bindingType") String bindingType);

    int deleteByUserIdAndType(@Param("userId") String userId, @Param("bindingType") String bindingType);
}
