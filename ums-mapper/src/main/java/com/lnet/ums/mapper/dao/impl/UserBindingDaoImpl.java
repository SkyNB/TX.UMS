package com.lnet.ums.mapper.dao.impl;

import com.lnet.model.ums.user.UserBinding;
import com.lnet.ums.mapper.dao.UserBindingDao;
import com.lnet.ums.mapper.dao.mappers.UserBindingMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public class UserBindingDaoImpl implements UserBindingDao {

    @Resource
    UserBindingMapper mapper;

    @Override
    public boolean exists(String userId, String bindingType) {
        return mapper.exists(userId, bindingType);
    }

    @Override
    public int update(String userId, String bindingValue) {
        return mapper.update(userId, bindingValue);
    }

    @Override
    public int bind(UserBinding userBinding) {
        return mapper.bind(userBinding);
    }

    @Override
    public int unBind(String userId, String bindingType) {
        return mapper.unBind(userId, bindingType);
    }

    @Override
    public List<UserBinding> getBindings(String userId) {
        return mapper.getBindings(userId);
    }

    @Override
    public UserBinding getBinding(String userId, String bindingType) {
        return mapper.getBinding(userId, bindingType);
    }

    @Override
    public int deleteByUserIdAndType(String userId, String bindingType) {
        return mapper.deleteByUserIdAndType(userId, bindingType);
    }
}
