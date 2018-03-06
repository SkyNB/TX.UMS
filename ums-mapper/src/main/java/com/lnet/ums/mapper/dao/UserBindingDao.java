package com.lnet.ums.mapper.dao;



import com.lnet.model.ums.user.UserBinding;

import java.util.List;

public interface UserBindingDao {

    boolean exists(String userId, String bindingType);

    int update(String userId, String bindingValue);

    int bind(UserBinding userBinding);

    int unBind(String userId, String bindingType);

    List<UserBinding> getBindings(String userId);

    UserBinding getBinding(String userId, String bindingType);

    int deleteByUserIdAndType(String userId, String bindingType);
}
