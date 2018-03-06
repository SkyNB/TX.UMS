package com.lnet.ums.contract.model;

public enum Permission {
    //订单权限管理
    ORDER_INSTRUCTION_VIEW("提货指令视图", "orderManage:instruction:view"),
    ORDER_ITORDER_VIEW("提货送货单视图", "orderManage:itOrder:view"),
    ORDER_FZORDER_VIEW("门店调拨单视图", "orderManage:fzOrder:view"),
    ORDER_CONFIRM_VIEW("审单确认视图", "orderManage:confirm:view"),
    ORDER_ORDERINF_VIEW("订单信息维护视图", "orderManage:orderInf:view"),
    ORDER_EXCEPTION_VIEW("异常信息视图", "orderManage:exception:view"),

    //运输权限管理
    TRANSPORT_DISPATCH_VIEW("调度视图", "transportManager:dispatch:view"),
    TRANSPORT_PACKAGE_VIEW("打包视图", "transportManager:package:view"),
    TRANSPORT_VEHICLEDISPATCHING_VIEW("派车视图", "transportManager:vehicleDispatching:view"),
    TRANSPORT_CONSIGN_VIEW("发运视图", "transportManager:consign:view"),
    TRANSPORT_TRANSFER_VIEW("调配视图", "transportManager:transfer:view"),
    TRANSPORT_PROGRESSTRACE_VIEW("信息跟踪视图", "transportManager:progressTrace:view"),
    TRANSPORT_RECEIPT_VIEW("回单管理视图", "transportManager:receipt:view"),

    //费用核算权限管理
    FEE_TRANSPORTPRICE_VIEW("运输报价视图", "fee:transportPrice:view"),
    FEE_RECEIVABLE_VIEW("应收视图", "fee:receivable:view"),
    FEE_PAYABLE_VIEW("应付视图", "fee:payable:view"),
    FEE_FEECHANGE_VIEW("费用变更视图", "fee:feeChange:view"),

    //供应商权限管理
    SUPPLY_VEHICLE_VIEW("车辆管理视图", "supply:vehicle:view"),
    SUPPLY_CARRIER_VIEW("承运商视图", "supply:carrier:view"),

    //客户权限管理
    CUSTOMER_BUSINESSGROUP_VIEW("业务组视图", "customer:businessGroup:view"),
    CUSTOMER_CUSTOMER_VIEW("客户视图", "customer:customer:view"),
    CUSTOMER_PROJECT_VIEW("项目视图", "customer:project:view"),
    CUSTOMER_ADDRESS_VIEW("收发地址视图", "customer:address:view"),
    CUSTOMER_GOODSARCHIVES_VIEW("商品档案视图", "customer:goodsArchives:view"),

    //系统权限管理
    SYSTEM_ORGANIZATION_VIEW("组织架构视图", "system:organization:view"),
    SYSTEM_SITE_VIEW("站点视图", "system:site:view"),
    SYSTEM_USER_VIEW("用户视图", "system:user:view"),
    SYSTEM_ROLE_VIEW("角色视图", "system:role:view"),
    SYSTEM_DISTRICT_VIEW("行政区视图", "system:district:view"),
    SYSTEM_EXPENSEACCOUNT_VIEW("费用科目视图", "system:expenseAccount:view"),

    //个人中心权限管理
    MYSELF_NOTIFY_VIEW("通知视图", "myself:notify:view");

    // 成员变量
    private String name;
    private String code;

    Permission(String name, String code) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (Permission c : Permission.values()) {
            if (code.equals(c.getCode())) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }
}