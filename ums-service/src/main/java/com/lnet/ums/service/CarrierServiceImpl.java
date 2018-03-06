package com.lnet.ums.service;

import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.CarrierService;
import com.lnet.model.ums.carrier.carrierDto.CarrierCreateDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierListDto;
import com.lnet.model.ums.carrier.carrierDto.CarrierUpdateDto;
import com.lnet.ums.mapper.dao.CarrierDao;
import com.lnet.model.ums.carrier.carrierEntity.Carrier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class CarrierServiceImpl implements CarrierService {
    @Autowired
    private CarrierDao carrierDao;

    @Override
    public Response<String> create(CarrierCreateDto carrierCreateDto) {
        try {
            Carrier carrier = toCarrier(carrierCreateDto);

            //条件验证
            Response validate = validate(carrier);
            if (!validate.isSuccess())
                return validate;

            //验证是否已存在
            Boolean isExists = carrierDao.exists(carrier.getCode(), null);
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            return carrierDao.insert(carrier) > 0 ? ResponseBuilder.success(carrier.getCarrierId(), "创建成功！") : ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<List<Response>> batchCreate(List<CarrierCreateDto> carrierCreateDtos) {
        try {
            List<Response> result = new ArrayList<>();

            //dto与实体的转化
            List<Carrier> carriers = carrierCreateDtos.stream().map(m -> toCarrier(m)).collect(Collectors.toList());

            //检查是否符合各种条件
            carriers.forEach(f -> {
                //条件验证
                {
                    Response response = ResponseBuilder.success();
                    Response validate = validate(f);
                    if (!validate.isSuccess()) {
                        response.setSuccess(false);
                        response.setMessage(f.getCode() + ":" + validate.getMessage());
                        result.add(response);

                    }
                }

                //验证是否已存在
                {
                    Response response = ResponseBuilder.success();
                    Boolean isExists = carrierDao.exists(f.getCode(), null);
                    if (isExists) {
                        response.setSuccess(false);
                        response.setMessage(f.getCode() + ":" + "编码已存在！");
                        result.add(response);
                    }
                }

            });

            //过滤出所有符合条件的carrier
            List<Carrier> matchCarriers = carriers.stream().filter(f -> (validate(f).isSuccess() && !carrierDao.exists(f.getCode(), null))).collect(Collectors.toList());

            //批量导入
            boolean flag = false;
            if (matchCarriers != null && 0 < matchCarriers.size())
                flag = carrierDao.batchInsert(matchCarriers) != matchCarriers.size();

            if (flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseBuilder.fail("导入失败！");
            }
            return ResponseBuilder.success(result, "导入成功！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<String> update(CarrierUpdateDto carrierUpdateDto) {
        try {
            Carrier carrier = Carrier.builder()
                    .carrierId(carrierUpdateDto.getCarrierId())
                    .code(carrierUpdateDto.getCode())
                    .name(carrierUpdateDto.getName())
                    .contactMan(carrierUpdateDto.getContactMan())
                    .telephoneNo(carrierUpdateDto.getTelephoneNo())
                    .mobilephoneNo(carrierUpdateDto.getMobilephoneNo())
                    .province(carrierUpdateDto.getProvince())
                    .city(carrierUpdateDto.getCity())
                    .area(carrierUpdateDto.getArea())
                    .street(carrierUpdateDto.getStreet())
                    .address(carrierUpdateDto.getAddress())
                    .districtCode(carrierUpdateDto.getDistrictCode())
                    .remark(carrierUpdateDto.getRemark())
                    .build();

            if (carrierUpdateDto.getType() != null)
                carrier.setType(Carrier.CarrierType.valueOf(carrierUpdateDto.getType().toString()));
            if (carrierUpdateDto.getPaymentType() != null)
                carrier.setPaymentType(Carrier.PaymentType.valueOf(carrierUpdateDto.getPaymentType().toString()));
            if (carrierUpdateDto.getSettleCycle() != null)
                carrier.setSettleCycle(Carrier.SettleCycle.valueOf(carrierUpdateDto.getSettleCycle().toString()));
            if (carrierUpdateDto.getCalculateType() != null)
                carrier.setCalculateType(Carrier.CalculateType.valueOf(carrierUpdateDto.getCalculateType().toString()));
            if (carrierUpdateDto.getTransportType() != null)
                carrier.setTransportType(Carrier.TransportType.valueOf(carrierUpdateDto.getTransportType().toString()));

            //条件验证
            Response validate = validate(carrier);
            if (!validate.isSuccess())
                return validate;

            //验证是否已存在
            Boolean isExists = carrierDao.exists(carrier.getCode(), carrier.getCarrierId());
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            return carrierDao.update(carrier) > 0 ? ResponseBuilder.success(carrier.getCarrierId(), "修改成功！") : ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<CarrierListDto>> getAll() {
        try {
            List<Carrier> carriers = carrierDao.getAll();

            List<CarrierListDto> carrierListDtos = carriers.stream().map(ele -> toCarrierListDto(ele)).collect(Collectors.toList());
            return ResponseBuilder.success(carrierListDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<CarrierListDto> get(String carrierId) {
        try {
            Assert.hasText(carrierId);

            Carrier carrier = carrierDao.get(carrierId);
            Assert.notNull(carrier, "获取数据失败！");

            CarrierListDto listDto = toCarrierListDto(carrier);
            return ResponseBuilder.success(listDto);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<CarrierListDto> getByCode(String code) {
        try {
            Assert.hasText(code);

            Carrier carrier = carrierDao.getByCode(code);
            Assert.notNull(carrier, "获取数据失败！");

            CarrierListDto listDto = toCarrierListDto(carrier);
            return ResponseBuilder.success(listDto);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<CarrierListDto>> getBranch(String branchCode) {
        try {
            Assert.hasText(branchCode);

            List<Carrier> carriers = carrierDao.getByBranchCode(branchCode);
            List<CarrierListDto> listDto = carriers.stream().map(ele -> {
                return toCarrierListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDto);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<CarrierListDto>> getBranchAvailable(String branchCode) {
        try {
            Assert.hasText(branchCode);

            List<Carrier> carriers = carrierDao.getByBranchCodeAndAvailable(branchCode);
            List<CarrierListDto> listDto = carriers.stream().map(ele -> {
                return toCarrierListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDto);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            Assert.hasText(code);

            return carrierDao.inactivate(code) > 0 ? ResponseBuilder.success("", "禁用成功！") : ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String code) {
        try {
            Assert.hasText(code);

            return carrierDao.activate(code) > 0 ? ResponseBuilder.success("", "启用成功！") : ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<CarrierListDto>> search(Map<String, Object> params) {
        try {
            List<Carrier> carriers = carrierDao.search(params);

            List<CarrierListDto> listDtos = carriers.stream().map(ele -> {
                return toCarrierListDto(ele);
            }).collect(Collectors.toList());

            return ResponseBuilder.success(listDtos);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    private Response validate(Carrier carrier) {
        try {
            Assert.notNull(carrier, "参数不能为空！");
            Assert.hasText(carrier.getCarrierId(), "主键不能为空！");
            Assert.hasText(carrier.getCode(), "编码不能为空！");
            Assert.hasText(carrier.getName(), "名称不能为空！");
            Assert.hasText(carrier.getDistrictCode(), "行政区代码不能为空！");

            //手机号验证
            Pattern phonePattern = Pattern.compile("1[3-8]\\d-\\d{4}-\\d{4}");
            Matcher phoneMatcher = phonePattern.matcher(carrier.getMobilephoneNo());
            Assert.isTrue(phoneMatcher.matches(), "手机号格式不正确！");

            return ResponseBuilder.success();
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    private CarrierListDto toCarrierListDto(Carrier carrier) {
        CarrierListDto carrierListDto = CarrierListDto.builder()
                .carrierId(carrier.getCarrierId())
                .branchCode(carrier.getBranchCode())
                .isActive(carrier.getIsActive())
                .build();
        carrierListDto.setCode(carrier.getCode());
        carrierListDto.setName(carrier.getName());
        carrierListDto.setContactMan(carrier.getContactMan());
        carrierListDto.setTelephoneNo(carrier.getTelephoneNo());
        carrierListDto.setMobilephoneNo(carrier.getMobilephoneNo());
        carrierListDto.setProvince(carrier.getProvince());
        carrierListDto.setCity(carrier.getCity());
        carrierListDto.setArea(carrier.getArea());
        carrierListDto.setStreet(carrier.getStreet());
        carrierListDto.setAddress(carrier.getAddress());
        carrierListDto.setDistrictCode(carrier.getDistrictCode());
        carrierListDto.setRemark(carrier.getRemark());

        if (carrier.getType() != null)
            carrierListDto.setType(CarrierDto.CarrierType.valueOf(carrier.getType().toString()));
        if (carrier.getPaymentType() != null)
            carrierListDto.setPaymentType(CarrierDto.PaymentType.valueOf(carrier.getPaymentType().toString()));
        if (carrier.getSettleCycle() != null)
            carrierListDto.setSettleCycle(CarrierDto.SettleCycle.valueOf(carrier.getSettleCycle().toString()));
        if (carrier.getCalculateType() != null)
            carrierListDto.setCalculateType(CarrierDto.CalculateType.valueOf(carrier.getCalculateType().toString()));
        if (carrier.getTransportType() != null)
            carrierListDto.setTransportType(CarrierDto.TransportType.valueOf(carrier.getTransportType().toString()));

        return carrierListDto;
    }

    private Carrier toCarrier(CarrierCreateDto carrierCreateDto) {
        Carrier carrier = Carrier.builder()
                .carrierId(Snowflake.getInstance().next())
                .code(carrierCreateDto.getCode())
                .name(carrierCreateDto.getName())
                .branchCode(carrierCreateDto.getBranchCode())
                .contactMan(carrierCreateDto.getContactMan())
                .telephoneNo(carrierCreateDto.getTelephoneNo())
                .mobilephoneNo(carrierCreateDto.getMobilephoneNo())
                .province(carrierCreateDto.getProvince())
                .city(carrierCreateDto.getCity())
                .area(carrierCreateDto.getArea())
                .street(carrierCreateDto.getStreet())
                .address(carrierCreateDto.getAddress())
                .districtCode(carrierCreateDto.getDistrictCode())
                .isActive(carrierCreateDto.getIsActive())
                .remark(carrierCreateDto.getRemark())
                .build();

        if (carrierCreateDto.getType() != null)
            carrier.setType(Carrier.CarrierType.valueOf(carrierCreateDto.getType().toString()));
        if (carrierCreateDto.getPaymentType() != null)
            carrier.setPaymentType(Carrier.PaymentType.valueOf(carrierCreateDto.getPaymentType().toString()));
        if (carrierCreateDto.getSettleCycle() != null)
            carrier.setSettleCycle(Carrier.SettleCycle.valueOf(carrierCreateDto.getSettleCycle().toString()));
        if (carrierCreateDto.getCalculateType() != null)
            carrier.setCalculateType(Carrier.CalculateType.valueOf(carrierCreateDto.getCalculateType().toString()));
        if (carrierCreateDto.getTransportType() != null)
            carrier.setTransportType(Carrier.TransportType.valueOf(carrierCreateDto.getTransportType().toString()));

        return carrier;
    }
}
