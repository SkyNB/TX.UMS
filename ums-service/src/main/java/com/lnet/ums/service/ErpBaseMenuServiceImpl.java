package com.lnet.ums.service;

import com.lnet.ums.contract.api.ErpBaseMenuService;
import com.lnet.model.ums.po.ErpBaseMenuCtPo;
import com.lnet.ums.mapper.dao.mappers.ErpBaseMenuMapper;
import com.lnet.model.ums.ErpBaseMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wanj on 2017/1/18.
 */
@Service
@Transactional
@Slf4j
public class ErpBaseMenuServiceImpl implements ErpBaseMenuService {

    @Resource
    ErpBaseMenuMapper erpBaseMenuMapper;

    //根据用户userId查找所拥有资源菜单
    @Override
    public List<ErpBaseMenuCtPo> selectErpBaseMenuList(String userId) {
        List<ErpBaseMenuCtPo> erpBaseMenuCtpo = new ArrayList<ErpBaseMenuCtPo>();
        List<ErpBaseMenu> erpBaseMenu=erpBaseMenuMapper.selectErpBaseMenuList(userId);
        if(erpBaseMenu!=null&&erpBaseMenu.size()>0){
            for(ErpBaseMenu erpBase : erpBaseMenu){
                ErpBaseMenuCtPo erpBaseMenuCtOne = new ErpBaseMenuCtPo();
                erpBaseMenuCtOne.setMenuid(erpBase.getMenuid());
                erpBaseMenuCtOne.setMenuname(erpBase.getMenuname());
                erpBaseMenuCtOne.setParentid(erpBase.getParentid());
                erpBaseMenuCtOne.setSortnumber(erpBase.getSortnumber());
                erpBaseMenuCtOne.setMenuurl(erpBase.getMenuurl());
                erpBaseMenuCtpo.add(erpBaseMenuCtOne);
            }
            return erpBaseMenuCtpo;
        }
        return null;
    }

}
