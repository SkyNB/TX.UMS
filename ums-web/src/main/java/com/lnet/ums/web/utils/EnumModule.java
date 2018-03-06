package com.lnet.ums.web.utils;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lnet.framework.core.EnumSerializer;
import com.lnet.model.ums.organization.Organization;
import com.lnet.model.ums.user.User;

public class EnumModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public EnumModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(Organization.type.class, new EnumSerializer());
        this.addSerializer(User.type.class, new EnumSerializer());
       /* this.addSerializer(District.DistrictType.class, new EnumSerializer());
        this.addSerializer(LogisticsOrder.OrderType.class, new EnumSerializer());
        this.addSerializer(LogisticOptions.TransportType.class, new EnumSerializer());
        this.addSerializer(LogisticsOrder.Status.class, new EnumSerializer());
        this.addSerializer(CollectingAddress.typeEnum.class, new EnumSerializer());
        this.addSerializer(Project.CalculateTypeEnum.class, new EnumSerializer());
        this.addSerializer(Project.HandoverTypeEnum.class, new EnumSerializer());
        this.addSerializer(Project.PaymentTypeEnum.class, new EnumSerializer());
        this.addSerializer(Project.ReceivableDataSourceEnum.class, new EnumSerializer());
        this.addSerializer(Project.SettleCycleEnum.class, new EnumSerializer());
        this.addSerializer(GoodsArchives.categoryEnum.class, new EnumSerializer());
        this.addSerializer(Package.PackageSize.class, new EnumSerializer());
        this.addSerializer(Package.wrapMaterial.class, new EnumSerializer());
        this.addSerializer(Package.Status.class, new EnumSerializer());
        this.addSerializer(VehicleDto.LeaseType.class, new EnumSerializer());
        this.addSerializer(VehicleDto.VehicleStatus.class, new EnumSerializer());
        this.addSerializer(CarrierDto.CarrierType.class, new EnumSerializer());
        this.addSerializer(CarrierDto.SettleCycle.class, new EnumSerializer());
        this.addSerializer(CarrierDto.PaymentType.class, new EnumSerializer());
        this.addSerializer(CarrierDto.CalculateType.class, new EnumSerializer());
        this.addSerializer(CarrierDto.TransportType.class, new EnumSerializer());
        this.addSerializer(Project.HandoverTypeEnum.class, new EnumSerializer());
        this.addSerializer(Project.PaymentTypeEnum.class, new EnumSerializer());
        this.addSerializer(Project.SettleCycleEnum.class, new EnumSerializer());
        this.addSerializer(Dispatch.statusEnum.class, new EnumSerializer());
        this.addSerializer(DispatchItem.orderDispatchTypeEnum.class, new EnumSerializer());
        this.addSerializer(DispatchQueryDto.OrderTypeEnum.class, new EnumSerializer());
        this.addSerializer(DispatchItem.orderTypeEnum.class, new EnumSerializer());
        this.addSerializer(CollectingInstruction.InstructionStatus.class, new EnumSerializer());
        this.addSerializer(PriceDto.CalcAttr.class, new EnumSerializer());
        this.addSerializer(PriceDto.CalcFormula.class, new EnumSerializer());
        this.addSerializer(PriceDto.OwnerType.class, new EnumSerializer());
        this.addSerializer(ConsignOrder.consignStatus.class, new EnumSerializer());
        this.addSerializer(Receipt.Status.class, new EnumSerializer());
        this.addSerializer(Receivable.Status.class, new EnumSerializer());
        this.addSerializer(ReceivableApproval.AuditStatus.class, new EnumSerializer());*/

        /*this.addSerializer(ExceptionDto.Classification.class, new EnumSerializer());
        this.addSerializer(ExceptionDto.Status.class, new EnumSerializer());

        this.addSerializer(OrderTransfer.statusEnum.class, new EnumSerializer());
        this.addSerializer(LogisticsOrder.dispatchTypeEnum.class, new EnumSerializer());
        this.addSerializer(LogisticOptions.CalculateType.class, new EnumSerializer());
        this.addSerializer(OrderPackInfoDto.Status.class, new EnumSerializer());
        this.addSerializer(LogisticOptions.HandoverType.class, new EnumSerializer());
        this.addSerializer(Payable.Status.class, new EnumSerializer());
        this.addSerializer(PayableApproval.AuditStatus.class, new EnumSerializer());
        this.addSerializer(PayableAdd.Status.class, new EnumSerializer());
        this.addSerializer(TaskTeam.TaskTeamType.class, new EnumSerializer());*/
    }
}
