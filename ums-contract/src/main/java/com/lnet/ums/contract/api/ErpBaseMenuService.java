package com.lnet.ums.contract.api;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.po.ErpBaseMenuCtPo;

import java.util.List;

/**
 * Created by Wanj on 2017/1/18.
 */
public interface ErpBaseMenuService {

    /**
     * 根据用户id查找所拥有的资源菜单
     * @param userId
     * @return
     */
    List<ErpBaseMenuCtPo> selectErpBaseMenuList(String userId);


}
