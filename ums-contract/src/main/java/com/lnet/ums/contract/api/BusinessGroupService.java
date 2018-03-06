package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.customer.customerEntity.BusinessGroup;

import java.util.List;
import java.util.Map;

public interface BusinessGroupService {
    /**
     * 业务组编码是否已存在
     *
     * @param code
     * @param businessGroupId
     * @return
     */
    Boolean exists(String code, String businessGroupId);

    /**
     * 添加业务组
     *
     * @param businessGroup
     * @return
     */
    Response create(BusinessGroup businessGroup);

    /**
     * 修改业务组
     *
     * @param businessGroup
     * @return
     */
    Response update(BusinessGroup businessGroup);

    /**
     * 根据code查询业务组信息
     *
     * @param code
     * @return
     */
    Response<BusinessGroup> getByCode(String code);

    /**
     * 查询所有的业务组信息
     *
     * @return
     */
    Response<List<BusinessGroup>> getAll();

    /**
     * 分页查询
     *
     * @param pageNumber
     * @param pageSize
     * @param params
     * @return
     */
    PageResponse<BusinessGroup> pageList(int pageNumber, int pageSize, Map<String, Object> params);
}
