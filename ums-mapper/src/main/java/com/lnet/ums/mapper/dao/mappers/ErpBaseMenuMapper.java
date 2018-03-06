package com.lnet.ums.mapper.dao.mappers;

//import com.lnet.model.ums.ErpBaseMenu;
import com.lnet.model.ums.ErpBaseMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErpBaseMenuMapper {
    int deleteByPrimaryKey(String menuid);

    int insert(ErpBaseMenu record);

    int insertSelective(ErpBaseMenu record);

    ErpBaseMenu selectByPrimaryKey(String menuid);

    int updateByPrimaryKeySelective(ErpBaseMenu record);

    int updateByPrimaryKey(ErpBaseMenu record);

    /**
     * 根据用户id查找所拥有的资源菜单
     * @param userId
     * @return
     */
    List<ErpBaseMenu> selectErpBaseMenuList(String userId);
}