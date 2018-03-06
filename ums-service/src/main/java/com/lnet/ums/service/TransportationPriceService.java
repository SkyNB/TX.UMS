package com.lnet.ums.service;

import com.lnet.base.contract.spi.DistrictService;
import com.lnet.framework.core.*;
import com.lnet.framework.excel.reader.ExcelReader;
import com.lnet.framework.excel.util.ExcelFormat;
import com.lnet.framework.util.ObjectQuery;
import com.lnet.model.base.District;
import com.lnet.model.cnaps.payDto.ReceivableOrderDto;
import com.lnet.model.cnaps.payEntity.*;
import com.lnet.model.tms.consign.consignEntity.ConsignOrder;
import com.lnet.model.tms.consign.consignEntity.ConsignOrderItem;
import com.lnet.model.ums.customer.customerDto.PriceCalcDto;
import com.lnet.model.ums.customer.customerDto.PriceDto;
import com.lnet.model.ums.customer.customerDto.PriceRangeDto;
import com.lnet.model.ums.customer.customerEntity.Customer;
import com.lnet.model.ums.customer.customerEntity.Project;
import com.lnet.model.ums.expense.ExpenseAccount;
import com.lnet.model.ums.transprotation.transprotationEntity.LogisticOptions;
import com.lnet.model.ums.transprotation.transprotationEntity.LogisticsOrder;
import com.lnet.model.ums.transprotation.transprotationDto.LogisticsOrderAllDto;
import com.lnet.ums.contract.api.*;
import com.lnet.model.ums.carrier.carrierDto.CarrierListDto;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleTypeDto;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/9/23.
 */
@Component
public class TransportationPriceService {


    public static final String RECEIVABLE = "110000";
    public static final String PAYABLE = "120100";

    /*PriceCalc*/
    @Resource
    ProjectService projectService;

    /*TransportionPrice*/
    @Resource
    PriceService priceService;

    // TODO: 2017/2/20 注释掉了微服务
    //@Resource
    DistrictService districtService;

    @Resource
    CustomerService customerService;
    @Resource
    CarrierService carrierService;

    @Resource
    VehicleTypeService vehicleTypeService;

    @Resource
    ExpenseAccountService expenseAccountService;


    public Response<PriceDto> create(PriceDto priceDto) {
        return priceService.create(priceDto);
    }

    public Response<PriceDto> get(String priceId) {
        return priceService.get(priceId);
    }

    public Response<PriceDto> update(PriceDto priceDto) {
        return priceService.update(priceDto);
    }

    public PageResponse<PriceDto> search(KendoGridRequest request) {
        PageResponse<PriceDto> response = priceService.pageList(request.getPage(), request.getPageSize(), request.getParams());
        convertDto(response.getBody());
        return response;
    }

    private void convertDto(List<PriceDto> priceDtos) {
        if (priceDtos != null) {
            List<CarrierListDto> carriers = carrierService.getAll().getBody();
            List<Customer> customers = customerService.getAll().getBody();
            List<ExpenseAccount> exaccts = expenseAccountService.findAll().getBody();
            List<VehicleTypeDto> vehicleListDtos = vehicleTypeService.finAll().getBody();
            priceDtos.forEach(priceDto -> {
                if (carriers != null && customers != null) {
                    CarrierListDto carrier = ObjectQuery.findOne(carriers, "code", priceDto.getOwnerCode());
                    if (carrier != null) {
                        priceDto.setOwnerName(carrier.getName());
                    } else {
                        Customer customer = ObjectQuery.findOne(customers, "code", priceDto.getOwnerCode());
                        if (customer != null) priceDto.setOwnerName(customer.getName());
                    }
                }
                List<District> orgins = districtService.getSuperior(priceDto.getOrgin()).getBody();
                if (orgins != null) {
                    priceDto.setOrginName(String.join("", orgins.stream().map(district -> district.getName()).collect(Collectors.toList())));
                }
                List<District> dests = districtService.getSuperior(priceDto.getDestination()).getBody();
                if (dests != null) {
                    priceDto.setDestinationName(String.join("", dests.stream().map(district -> district.getName()).collect(Collectors.toList())));
                }
                ExpenseAccount expenseAccount = ObjectQuery.findOne(exaccts, "code", priceDto.getExpenseAccount());
                if (expenseAccount != null) {
                    priceDto.setExpenseName(expenseAccount.getName());
                }
                if (priceDto.getVehicleType() != null) {
                    VehicleTypeDto vehicleTypeDto = ObjectQuery.findOne(vehicleListDtos, "vehicleTypeId", priceDto.getVehicleType());
                    if (vehicleTypeDto != null) {
                        priceDto.setVehicleTypeName(vehicleTypeDto.getName());
                    }
                }
            });
        }
    }

    public ListResponse<PriceRangeDto> findRanges(String priceId) {
        return priceService.findByPriceId(priceId);
    }


    public Response<List<Response>> importPrice(InputStream stream, String userId, String branchCode) {
        try {
            List<PriceDto> priceDtos = new ArrayList<>();
            List<District> districts = districtService.getAll().getBody();
            List<CarrierListDto> carriers = carrierService.getBranch(branchCode).getBody();
            List<Customer> customers = customerService.getAvailable().getBody();
            List<VehicleTypeDto> vehicles = vehicleTypeService.finAll().getBody();
            List<ExpenseAccount> expenseAccounts = expenseAccountService.findAll().getBody();
            List<Response> result = new ArrayList<>();
            List<String> data = new ArrayList<>();
            List<PriceRangeDto> rangeDtoList = ExcelReader.readByColumnName(stream, ExcelFormat.OFFICE2007, 0, 0, row -> {

                Response response = ResponseBuilder.success(null, "");

                StringBuilder sb = new StringBuilder();
                sb.append(row.getColumnValue("报价对象"));
                sb.append(row.getColumnValue("始发城市"));
                sb.append(row.getColumnValue("目的城市"));
                sb.append(row.getColumnValue("运输方式"));
                sb.append(row.getColumnValue("订单类型"));
                sb.append(row.getColumnValue("车型"));
                sb.append(row.getColumnValue("计算公式"));
                sb.append(row.getColumnValue("计费属性"));
                sb.append(row.getColumnValue("费用科目"));
                sb.append(row.getColumnValue("产品类型"));
                //region 读取区间数据
                // 最低收费	最小区间	最大区间	单价
                PriceRangeDto range = new PriceRangeDto();
                try {
                    range.setMinAmount(new BigDecimal(row.getColumnValue("最低收费").toString()));
                } catch (Exception e) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "最低收费请填写数字；");
                    result.add(response);
                    return null;
                }
                try {
                    range.setRangeStart(new BigDecimal(row.getColumnValue("最小区间").toString()));
                } catch (Exception e) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "最小区间请填写数字；");
                    result.add(response);
                    return null;
                }
                if (!StringUtils.isEmpty(row.getColumnValue("最大区间"))) {
                    try {
                        range.setRangeEnd(new BigDecimal(row.getColumnValue("最大区间").toString()));
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "最大区间请填写数字；");
                        result.add(response);
                        return null;
                    }
                }
                try {
                    range.setUnitPrice(new BigDecimal(row.getColumnValue("单价").toString()));
                } catch (Exception e) {
                    response.setSuccess(false);
                    response.setMessage(response.getMessage() + "单价请填写数字；");
                    result.add(response);
                    return null;
                }
                range.setPriceId(sb.toString());
                //endregion

                //region 读取报价
                if (!data.contains(sb.toString())) {
                    data.add(sb.toString());
                    PriceDto price = new PriceDto();
                    CarrierListDto carrier = ObjectQuery.findOne(carriers, "code", row.getColumnValue("报价对象"));
                    if (null == carrier) {
                        Customer customer = ObjectQuery.findOne(customers, "code", row.getColumnValue("报价对象"));
                        if (null == customer) {
                            response.setSuccess(false);
                            response.setMessage(response.getMessage() + "报价对象编码不存在；");
                            result.add(response);
                            return null;
                        } else {
                            price.setOwnerType(PriceDto.OwnerType.CUSTOMER);
                        }
                    } else {
                        price.setOwnerType(PriceDto.OwnerType.CARRIER);
                    }
                    price.setOwnerCode(row.getColumnValue("报价对象").toString());

                    District orgin = ObjectQuery.findOne(districts, "name", row.getColumnValue("始发城市"));
                    if (orgin != null) {
                        price.setOrgin(orgin.getCode());
                    } else {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "始发城市不存在；");
                        result.add(response);
                        return null;
                    }
                    District dest = ObjectQuery.findOne(districts, "name", row.getColumnValue("目的城市"));
                    if (dest != null) {
                        price.setDestination(dest.getCode());
                    } else {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "目的城市不存在；");
                        result.add(response);
                        return null;
                    }
                    LogisticOptions.TransportType transportType = getTransportType(row.getColumnValue("运输方式"));
                    if (transportType == null) {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "运输方式不存在；");
                        result.add(response);
                        return null;
                    } else {
                        price.setTransportType(transportType.name());
                    }
                    LogisticsOrder.OrderType orderType = getOrderType(row.getColumnValue("订单类型"));
                    if (orderType == null) {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "订单类型不存在；");
                        result.add(response);
                        return null;
                    } else {
                        price.setOrderType(orderType.name());
                    }
                    PriceDto.CalcAttr calcAttr = getCalcAttr(row.getColumnValue("计费属性"));
                    if (calcAttr == null) {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "计费属性不存在；");
                        result.add(response);
                        return null;
                    } else {
                        price.setCalcAttr(calcAttr);
                    }
                    PriceDto.CalcFormula calcFormula = getCalcFormula(row.getColumnValue("计算公式"));
                    if (calcFormula == null) {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "计算公式不存在；");
                        result.add(response);
                        return null;
                    } else {
                        price.setCalcFormula(calcFormula);
                    }
                    ExpenseAccount account = ObjectQuery.findOne(expenseAccounts, "code", row.getColumnValue("费用科目"));
                    if (account == null) {
                        response.setSuccess(false);
                        response.setMessage(response.getMessage() + "费用科目不存在；");
                        result.add(response);
                        return null;
                    } else {
                        price.setExpenseAccount(account.getCode());
                    }
                    price.setProductCategory((String) row.getColumnValue("产品类型"));
                    price.setBranchCode(branchCode);
                    price.setRemark((String) row.getColumnValue("备注"));
                    price.setPriceId(sb.toString());
                    priceDtos.add(price);
                }
                //endregion
                return range;
            });
            Map<String, List<PriceRangeDto>> groupedPriceIds = rangeDtoList.stream().filter(range -> range != null).collect(Collectors.groupingBy(PriceRangeDto::getPriceId));
            for (PriceDto priceDto : priceDtos) {
                priceDto.setRangeDto(groupedPriceIds.get(priceDto.getPriceId()));
            }
            if (priceDtos.size() > 0) {
                Response response = priceService.batchCreate(priceDtos);
                if (!response.isSuccess()) {
                    return response.setBody(result);
                }
            }
            Response response = ResponseBuilder.success(result);
            if (!result.isEmpty()) {
                response.setSuccess(false);
            }
            return response;
        } catch (IOException e) {
            return ResponseBuilder.fail(e);
        }
    }

    private LogisticOptions.TransportType getTransportType(Object text) {
        for (LogisticOptions.TransportType type : LogisticOptions.TransportType.values()) {
            if (text != null && text.equals(type.getText())) {
                return type;
            }
        }
        return null;
    }

    private PriceDto.CalcAttr getCalcAttr(Object text) {
        for (PriceDto.CalcAttr type : PriceDto.CalcAttr.values()) {
            if (text != null && text.equals(type.getText())) {
                return type;
            }
        }
        return null;
    }

    private PriceDto.CalcFormula getCalcFormula(Object text) {
        for (PriceDto.CalcFormula type : PriceDto.CalcFormula.values()) {
            if (text != null && text.equals(type.getText())) {
                return type;
            }
        }
        return null;
    }

    private LogisticsOrder.OrderType getOrderType(Object text) {
        for (LogisticsOrder.OrderType type : LogisticsOrder.OrderType.values()) {
            if (text != null && text.equals(type.getText())) {
                return type;
            }
        }
        return null;
    }




    /* PriceCalc*/
    public Response<ReceivableOrderDto> calculateReceivable(LogisticsOrderAllDto order, Project.ReceivableDataSourceEnum receivableSource) {
        try {
            ReceivableOrderDto receivable = null;
            Response<Project> projectResponse = projectService.getProject(order.getBranchCode(), order.getCustomerCode());
            Assert.isTrue(null != projectResponse.getBody(), "项目不存在");
            if (null != projectResponse.getBody() && null != projectResponse.getBody().getReceivableDataSource()
                    && projectResponse.getBody().getReceivableDataSource().equals(receivableSource)) {
                Project project = projectResponse.getBody();
                receivable = new ReceivableOrderDto();
                ReceivableCalc calc = ReceivableCalc.builder()
                        .deliveryCompany(order.getDeliveryCompany())
                        .deliveryContacts(order.getDeliveryContacts())
                        .destinationCode(order.getDestinationCode())
                        .orginCode(order.getOrginCode())
                        .orderDate(order.getOrderDate())
                        .orderNo(order.getOrderNo())
                        .totalItemQty(order.getTotalItemQty())
                        .totalPackageQty(order.getTotalPackageQty())
                        .totalWeight(order.getTotalWeight())
                        .totalVolume(order.getTotalVolume())
                        .transportType(order.getTransportType().name())
                        .build();
                receivable.setReceivableCalc(calc);
                receivable.setBillingCycle(project.getSettleCycle().name());
                receivable.setBranchCode(order.getBranchCode());
                receivable.setCustomerCode(order.getCustomerCode());
                receivable.setCreateUserId(order.getCreatedBy());
                receivable.setCalcAttr(order.getCalculateType().name());
                receivable.setSiteCode(order.getSiteCode());
                receivable.setPaymentType(project.getPaymentType().name());
                receivable.setOrderNo(order.getOrderNo());
                List<ExpenseAccount> expenseAccounts = expenseAccountService.findChildren(RECEIVABLE).getBody();
                Map<String, BigDecimal> account = null;
                BigDecimal totalAmount = new BigDecimal(0);
                switch (order.getCalculateType()) {
                    case WEIGHT:
                        account = calculate(new PriceCalcDto(order.getCustomerCode(), order.getOrginCode(), order.getDestinationCode(),
                                order.getCalculateType().name(), order.getTransportType().name(), order.getTotalVolume())).getBody();
                        break;
                    case VOLUME:
                        account = calculate(new PriceCalcDto(order.getCustomerCode(), order.getOrginCode(), order.getDestinationCode(),
                                order.getCalculateType().name(), order.getTransportType().name(), order.getTotalVolume())).getBody();
                        break;
                    case FREQUENCY://车次
//                    account = calculate(order.getCustomerCode(),order.getOrginCode(),order.getDestinationCode(),
//                        order.getCalculateType().name(),order.getTransportType().name(),null,null,order.getTotalVolume()).getBody();
                        break;
                    case AMOUNT:
                        account = calculate(new PriceCalcDto(order.getCustomerCode(), order.getOrginCode(), order.getDestinationCode(),
                                PriceDto.CalcAttr.PRODUCT_QTY.name(), order.getTransportType().name(), new BigDecimal(order.getTotalItemQty()))).getBody();
                    case VOLUME_OR_WEIGHT:
                        Map<String, BigDecimal> accByWeight = calculate(new PriceCalcDto(order.getCustomerCode(), order.getOrginCode(), order.getDestinationCode(),
                                PriceDto.CalcAttr.WEIGHT.name(), order.getTransportType().name(), order.getTotalWeight())).getBody();
                        Map<String, BigDecimal> accByVolume = calculate(new PriceCalcDto(order.getCustomerCode(), order.getOrginCode(), order.getDestinationCode(),
                                PriceDto.CalcAttr.VOLUME.name(), order.getTransportType().name(), order.getTotalVolume())).getBody();
                        if (null != accByVolume && null != accByWeight) {
                            BigDecimal totalByWeight = new BigDecimal(0);
                            BigDecimal totalByVolume = new BigDecimal(0);
                            for (Map.Entry<String, BigDecimal> decimalEntry : accByWeight.entrySet()) {
                                totalByWeight = totalByWeight.add(decimalEntry.getValue());
                            }
                            for (Map.Entry<String, BigDecimal> decimalEntry : accByVolume.entrySet()) {
                                totalByVolume = totalByVolume.add(decimalEntry.getValue());
                            }
                            if (totalByVolume.compareTo(totalByWeight) > 0) {//取较低的
                                receivable.setCalcAttr(PriceDto.CalcAttr.WEIGHT.name());
                                account = accByWeight;
                            } else {
                                receivable.setCalcAttr(PriceDto.CalcAttr.VOLUME.name());
                                account = accByVolume;
                            }
                        } else if (null == accByVolume) {
                            receivable.setCalcAttr(PriceDto.CalcAttr.WEIGHT.name());
                            account = accByWeight;
                        } else {
                            receivable.setCalcAttr(PriceDto.CalcAttr.VOLUME.name());
                            account = accByVolume;
                        }
                    case CARGO_TYPE_AND_VOLUME://// TODO: 2016/11/8
                        receivable.setCalcAttr(PriceDto.CalcAttr.VOLUME.name());
                        account = calculate(new PriceCalcDto(order.getCustomerCode(), order.getOrginCode(), order.getDestinationCode(),
                                PriceDto.CalcAttr.VOLUME.name(), order.getTransportType().name(), order.getItems().get(0).getGoodsName(), null, order.getTotalWeight())).getBody();
                        break;
                    default:
                        account = calculate(new PriceCalcDto(order.getCustomerCode(), order.getOrginCode(), order.getDestinationCode(),
                                PriceDto.CalcAttr.PRODUCT_QTY.name(), order.getTransportType().name(), new BigDecimal(order.getTotalItemQty()))).getBody();


                }
                List<ReceivableAccount> accounts = new ArrayList<>();
                if (null == account) account = new HashMap<>();
                for (Map.Entry<String, BigDecimal> decimalEntry : account.entrySet()) {
                    totalAmount = totalAmount.add(decimalEntry.getValue());
                }
                if (null != expenseAccounts) {
                    for (ExpenseAccount expenseAccount : expenseAccounts) {
                        BigDecimal amount = account.get(expenseAccount.getCode()) == null ? new BigDecimal(0) : account.get(expenseAccount.getCode());
                        accounts.add(ReceivableAccount.builder()
                                .calculateAmount(amount.setScale(2, RoundingMode.HALF_EVEN))
                                .accountCode(expenseAccount.getCode())
                                .amount(amount.setScale(2, RoundingMode.HALF_EVEN))
                                .build());
                    }
                }
                receivable.setCalculateAmount(totalAmount);
                receivable.setTotalAmount(totalAmount);
                receivable.setAccounts(accounts);
            }
            return ResponseBuilder.success(receivable);
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    public Response<List<ReceivableOrderDto>> calculateReceivable(List<LogisticsOrderAllDto> orders, Project.ReceivableDataSourceEnum receivableSource) {
        try {
            Assert.notEmpty(orders);
            List<ReceivableOrderDto> result = new ArrayList<>();
            orders.forEach(consignOrder -> result.add(calculateReceivable(consignOrder, receivableSource).getBody()));
            return ResponseBuilder.list(result);
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            return ResponseBuilder.listFail(e);
        }
    }

    public Response<Payable> calculatePayable(ConsignOrder consignOrder) {
        try {
            Assert.hasText(consignOrder.getCalculateType(), "计费类型不能为空");
            Assert.hasText(consignOrder.getCarrierCode(), "承运商不能为空");
            Assert.hasText(consignOrder.getStartCityCode(), "起始地不能为空");
            Assert.hasText(consignOrder.getDestCityCode(), "目的地不能为空");
            Payable payable = Payable.builder()
                    .billingCycle(consignOrder.getSettlementCycle())
                    .paymentType(consignOrder.getPaymentType())
                    .branchCode(consignOrder.getBranchCode())
                    .createUserId(consignOrder.getModifiedBy())
                    .ownerCode(consignOrder.getCarrierCode())
                    .ownerType(Payable.OwnerType.CARRIER)
                    .billingCycle(consignOrder.getSettlementCycle())
                    .siteCode(consignOrder.getSiteCode())
                    .calcAttr(consignOrder.getCalculateType())
                    .sourceNo(consignOrder.getConsignOrderNo())
                    .build();
            Map<String, BigDecimal> account = null;
            BigDecimal totalAmount = new BigDecimal(0);
            //region 计算应付
            switch (consignOrder.getCalculateType()) {
                case "WEIGHT":
                    payable.setCalcAttr(PriceDto.CalcAttr.WEIGHT.name());
                    account = calculate(new PriceCalcDto(consignOrder.getCarrierCode(), consignOrder.getStartCityCode(), consignOrder.getDestCityCode(),
                            PriceDto.CalcAttr.WEIGHT.name(), consignOrder.getTransportType(), consignOrder.getTotalVolume())).getBody();
                    break;
                case "CARGO_TYPE_AND_VOLUME"://// TODO: 2016/11/8
                case "VOLUME":
                    payable.setCalcAttr(PriceDto.CalcAttr.VOLUME.name());
                    account = calculate(new PriceCalcDto(consignOrder.getCarrierCode(), consignOrder.getStartCityCode(), consignOrder.getDestCityCode(),
                            PriceDto.CalcAttr.VOLUME.name(), consignOrder.getTransportType(), consignOrder.getTotalVolume())).getBody();
                    break;
                case "FREQUENCY"://车次
//                    account = calculate(consignOrder.getCustomerCode(),consignOrder.getStartCityCode(),consignOrder.getDestCityCode(),
//                        consignOrder.getCalculateType().name(),consignOrder.getTransportType().name(),null,null,consignOrder.getTotalVolume()).getBody();
                    break;
                case "AMOUNT":
                    payable.setCalcAttr(PriceDto.CalcAttr.PACKAGE_QTY.name());
                    account = calculate(new PriceCalcDto(consignOrder.getCarrierCode(), consignOrder.getStartCityCode(), consignOrder.getDestCityCode(),
                            PriceDto.CalcAttr.PACKAGE_QTY.name(), consignOrder.getTransportType(), new BigDecimal(consignOrder.getTotalPackageQuantity()))).getBody();
                    break;
                case "VOLUME_OR_WEIGHT":
                    Map<String, BigDecimal> accByWeight = calculate(new PriceCalcDto(consignOrder.getCarrierCode(), consignOrder.getStartCityCode(), consignOrder.getDestCityCode(),
                            PriceDto.CalcAttr.WEIGHT.name(), consignOrder.getTransportType(), consignOrder.getTotalWeight())).getBody();
                    Map<String, BigDecimal> accByVolume = calculate(new PriceCalcDto(consignOrder.getCarrierCode(), consignOrder.getStartCityCode(), consignOrder.getDestCityCode(),
                            PriceDto.CalcAttr.VOLUME.name(), consignOrder.getTransportType(), consignOrder.getTotalVolume())).getBody();
                    if (accByVolume != null && accByWeight != null) {
                        BigDecimal totalByWeight = new BigDecimal(0);
                        BigDecimal totalByVolume = new BigDecimal(0);
                        for (Map.Entry<String, BigDecimal> decimalEntry : accByWeight.entrySet()) {
                            totalByWeight = totalByWeight.add(decimalEntry.getValue());
                        }
                        for (Map.Entry<String, BigDecimal> decimalEntry : accByVolume.entrySet()) {
                            totalByVolume = totalByVolume.add(decimalEntry.getValue());
                        }
                        if (totalByVolume.compareTo(totalByWeight) < 0) {//取较高的
                            account = accByWeight;
                            payable.setCalcAttr(PriceDto.CalcAttr.WEIGHT.name());
                        } else {
                            account = accByVolume;
                            payable.setCalcAttr(PriceDto.CalcAttr.VOLUME.name());
                        }
                    } else if (accByVolume == null) {
                        payable.setCalcAttr(PriceDto.CalcAttr.WEIGHT.name());
                        account = accByWeight;
                    } else {
                        account = accByVolume;
                        payable.setCalcAttr(PriceDto.CalcAttr.VOLUME.name());
                    }
                    break;

                default:
                    payable.setCalcAttr(PriceDto.CalcAttr.VOLUME.name());
                    account = calculate(new PriceCalcDto(consignOrder.getCarrierCode(), consignOrder.getStartCityCode(), consignOrder.getDestCityCode(),
                            PriceDto.CalcAttr.VOLUME.name(), consignOrder.getTransportType(), consignOrder.getTotalVolume())).getBody();
            }
            //endregion
            if (account == null) account = new HashMap<>();
            for (Map.Entry<String, BigDecimal> decimalEntry : account.entrySet()) {
                totalAmount = totalAmount.add(decimalEntry.getValue());
            }
            List<PayableAccount> accounts = new ArrayList<>();
            List<ExpenseAccount> expenseAccounts = expenseAccountService.findChildren(PAYABLE).getBody();
            List<PayableProportion> proportions = new ArrayList<>();

            if (null != expenseAccounts) {
                //region 应付明细
                for (ExpenseAccount expenseAccount : expenseAccounts) {
                    BigDecimal amount = account.get(expenseAccount.getCode()) == null ? new BigDecimal(0) : account.get(expenseAccount.getCode());
                    accounts.add(PayableAccount.builder()
                            .accountCode(expenseAccount.getCode())
                            .amount(amount.setScale(2, RoundingMode.HALF_EVEN))
                            .calculateAmount(amount.setScale(2, RoundingMode.HALF_EVEN))
                            .build());
                }
                //endregion

                //region 创建分摊
                List<ConsignOrderItem> items = consignOrder.getItems();
                BigDecimal totalScale = new BigDecimal(0);
                for (int i = 0, itemsSize = items.size(); i < itemsSize; i++) {
                    ConsignOrderItem consignOrderItem = items.get(i);
                    BigDecimal scale = new BigDecimal(0);
                    switch (payable.getCalcAttr()) {
                        case "WEIGHT":
                            scale = consignOrderItem.getWeight().divide(consignOrder.getTotalWeight(), 4, RoundingMode.HALF_EVEN);
                            break;
                        case "VOLUME":
                            scale = consignOrderItem.getVolume().divide(consignOrder.getTotalVolume(), 4, RoundingMode.HALF_EVEN);
                            break;
                        case "PACKAGE_QTY":
                            scale = new BigDecimal(consignOrderItem.getPackageQuantity().doubleValue() / consignOrder.getTotalPackageQuantity());
                            break;
                        default:
                            scale = BigDecimal.valueOf(1.0 / consignOrder.getItems().size());
                            break;
                    }
                    if (i == items.size() - 1) {
                        scale = new BigDecimal(1).subtract(totalScale);
                    }
                    Assert.isTrue(scale.compareTo(new BigDecimal(0)) >= 0, "分摊比例不能小于0");
                    totalScale = totalScale.add(scale);
                    BigDecimal finalScale = scale;
                    List<PayableProportionAccount> proportionAccounts = accounts.stream()
                            .map(payableAccount ->
                                    PayableProportionAccount.builder()
                                            .accountCode(payableAccount.getAccountCode())
                                            .amount(payableAccount.getCalculateAmount().multiply(finalScale).setScale(2, RoundingMode.HALF_EVEN))
                                            .build())
                            .collect(Collectors.toList());
                    proportions.add(PayableProportion.builder()
                            .amount(totalAmount.multiply(scale).setScale(2, RoundingMode.HALF_EVEN))
                            .orderNo(consignOrderItem.getOrderNo())
                            .proportionAccounts(proportionAccounts)
                            .proportionType(payable.getCalcAttr())
                            .scale(scale)
                            .build());
                }
                Assert.isTrue(totalScale.compareTo(new BigDecimal(1)) == 0, "未完全分摊");
                //endregion
            }
            payable.setCalculateAmount(totalAmount.setScale(2, RoundingMode.HALF_EVEN));
            payable.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_EVEN));
            payable.setAccounts(accounts);

            payable.setProportions(proportions);

            return ResponseBuilder.success(payable);
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    public Response<List<Payable>> calculatePayable(List<ConsignOrder> consignOrders) {
        try {
            Assert.notEmpty(consignOrders);
            List<Payable> result = new ArrayList<>();
            consignOrders.forEach(consignOrder -> result.add(calculatePayable(consignOrder).getBody()));
            return ResponseBuilder.list(result);
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            return ResponseBuilder.listFail(e);
        }
    }


    /**
     * @param priceCalcDto 报价计算模型
     * @return Map<费用科目，价格>
     */
    public Response<Map<String, BigDecimal>> calculate(PriceCalcDto priceCalcDto) {
        try {
            Map<String, BigDecimal> result = new HashMap<>();
            String ownerCode = priceCalcDto.getOwnerCode();
            String calcAttr = priceCalcDto.getCalcAttr();
            String transportType = priceCalcDto.getTransportType();
            String orgin = priceCalcDto.getOrginCode();
            String dest = priceCalcDto.getDestCode();
            String vehicleType = priceCalcDto.getVehicleType();
            String productCategory = priceCalcDto.getProductCategory();
            BigDecimal value = priceCalcDto.getValue();
            Assert.hasText(ownerCode, "ownerCode 不能为null");
            Assert.notNull(calcAttr, "calcAttr 不能为null");
            Assert.hasText(transportType, "transportType 不能为null");
            Assert.hasText(orgin, "orgin 不能为null");
            Assert.hasText(dest, "dest 不能为null");
            Assert.notNull(value, "dest 不能为null");
            List<PriceDto> priceDtos = priceService.findByPrice(ownerCode, calcAttr, transportType).getBody();
            if (priceDtos != null && priceDtos.size() > 0) {
                if (!StringUtils.isEmpty(productCategory)) {
                    priceDtos = priceDtos.stream().filter(priceDto -> productCategory.equals(priceDto.getProductCategory())).collect(Collectors.toList());
                }
                if (!StringUtils.isEmpty(vehicleType)) {
                    priceDtos = priceDtos.stream().filter(priceDto -> vehicleType.equals(priceDto.getVehicleType())).collect(Collectors.toList());
                }
            }
            if (priceDtos == null || priceDtos.isEmpty()) {
                return ResponseBuilder.fail("没有匹配的报价");
            }
            Map<String, List<PriceDto>> groupedByAccount = priceDtos.stream().collect(Collectors.groupingBy(PriceDto::getExpenseAccount));
            for (Map.Entry<String, List<PriceDto>> entry : groupedByAccount.entrySet()) {
                PriceDto priceDto = filterByDistrict(entry.getValue(), orgin, dest);
                if (priceDto != null) {
                    result.put(entry.getKey(), getAmount(priceDto, value));
                }
            }
            if (result.size() > 0)
                return ResponseBuilder.success(result);
            else
                return ResponseBuilder.fail("没有匹配的报价");
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    private BigDecimal getAmount(PriceDto priceDto, BigDecimal value) {
        PriceRangeDto rangeDto = priceService.findByPriceId(priceDto.getPriceId(), value).getBody();
        BigDecimal amount = new BigDecimal(0);
        if (rangeDto != null) {
            /*add 加  subtract 减    multiply 乘 divide除
        RANGED("区间"),
        INCREASED("续增累加"),
        UNITED("乘积");*/
            BigDecimal unit = rangeDto.getUnitPrice() == null ? new BigDecimal(0) : rangeDto.getUnitPrice();
            switch (priceDto.getCalcFormula()) {
                case INCREASED://续增
                    if (value.compareTo(rangeDto.getRangeStart()) > 0) {
                        amount = rangeDto.getMinAmount().add(value.subtract(rangeDto.getRangeStart()).multiply(unit));
                    } else {
                        amount = rangeDto.getMinAmount();
                    }
                    break;
                case RANGED:
                    amount = value.multiply(unit);
                    amount = amount.compareTo(rangeDto.getMinAmount()) > 0 ? amount : rangeDto.getMinAmount();
                    break;
                case UNITED:
                    amount = value.multiply(unit);
                    break;
            }
        }
        return amount;
    }

    /**
     * 根据起始地目的地过滤,详细地址查询不到时默认查询上级地址
     *
     * @param priceDtos
     * @param orgin
     * @param dest
     * @return
     */
    public PriceDto filterByDistrict(List<PriceDto> priceDtos, String orgin, String dest) {
        Integer[] length = new Integer[]{2, 4, 6, 9};
        for (int i = 0; i < length.length; i++) {
            final int finalI = length[i];
            if (finalI <= orgin.length()) {
                for (int j = 0; j < length.length; j++) {
                    final int finalJ = length[j];
                    if (finalJ <= dest.length()) {
                        List<PriceDto> result = new ArrayList<>();
                        result = priceDtos.stream().filter(priceDto ->
                                orgin.substring(0, finalI).equals(priceDto.getOrgin().substring(0, finalI))
                                        && dest.substring(0, finalJ).equals(priceDto.getDestination().substring(0, finalJ))
                        )
                                .collect(Collectors.toList());

                        if (result.size() == 1) {
                            return result.get(0);
                        }
                    }
                }
            }
        }
        return null;
    }

}
