package com.lnet.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.ShipAddressService;
import com.lnet.model.ums.customer.customerEntity.ShipAddress;
import com.lnet.ums.mapper.dao.ShipAddressDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
@Slf4j
public class ShipAddressServiceImpl implements ShipAddressService {
    @Autowired
    private ShipAddressDao shipAddressDao;

    @Override
    public Response<String> create(ShipAddress shipAddress) {
        try {
            //参数验证
            Response validate = validate(shipAddress);
            if (!validate.isSuccess())
                return validate;

            //检验编码是否已存在
            Boolean isExists = shipAddressDao.exists(shipAddress.getCode(), null);
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            shipAddress.setShipAddressId(Snowflake.getInstance().next());

            return shipAddressDao.insert(shipAddress) > 0 ? ResponseBuilder.success(shipAddress.getShipAddressId(), "创建成功！") : ResponseBuilder.fail("创建失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<String> update(ShipAddress shipAddress) {
        try {
            //参数验证
            Response validate = validate(shipAddress);
            if (!validate.isSuccess())
                return validate;

            //检验编码是否已存在
            Boolean isExists = shipAddressDao.exists(shipAddress.getCode(), shipAddress.getShipAddressId());
            if (isExists)
                return ResponseBuilder.fail("编码已存在！");

            return shipAddressDao.update(shipAddress) > 0 ? ResponseBuilder.success(shipAddress.getShipAddressId(), "修改成功！") : ResponseBuilder.fail("修改失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<ShipAddress>> getAll() {
        try {
            List<ShipAddress> shipAddresses = shipAddressDao.getAll();
            return ResponseBuilder.success(shipAddresses);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<ShipAddress> get(String shipAddressId) {
        try {
            Assert.hasText(shipAddressId);

            ShipAddress shipAddress = shipAddressDao.get(shipAddressId);
            Assert.notNull(shipAddress, "获取数据失败！");

            return ResponseBuilder.success(shipAddress);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<ShipAddress> getByCode(String code) {
        try {
            Assert.hasText(code);

            ShipAddress shipAddress = shipAddressDao.getByCode(code);
            Assert.notNull(shipAddress, "获取数据失败！");

            return ResponseBuilder.success(shipAddress);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<ShipAddress>> getCarrier(String carrierCode) {
        try {
            Assert.hasText(carrierCode);

            List<ShipAddress> shipAddress = shipAddressDao.getByCarrierCode(carrierCode);

            return ResponseBuilder.success(shipAddress);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<ShipAddress>> getCarrierAvailable(String carrierCode) {
        try {
            Assert.hasText(carrierCode);

            List<ShipAddress> shipAddress = shipAddressDao.getByCarrierCodeAndAvailable(carrierCode);

            return ResponseBuilder.success(shipAddress);
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response inactivate(String code) {
        try {
            Assert.hasText(code);

            return shipAddressDao.inactivate(code) > 0 ? ResponseBuilder.success("", "禁用成功！") : ResponseBuilder.fail("禁用失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response activate(String code) {
        try {
            Assert.hasText(code);

            return shipAddressDao.activate(code) > 0 ? ResponseBuilder.success("", "启用成功！") : ResponseBuilder.fail("启用失败！");
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public PageResponse<ShipAddress> pageList(int pageNo, int pageSize, Map<String, Object> params) {
        try {
            Assert.notNull(pageNo);
            Assert.notNull(pageSize);

            PageHelper.startPage(pageNo, pageSize);

            List<ShipAddress> shipAddresses = shipAddressDao.pageList(params);
            PageInfo<ShipAddress> pageInfo = new PageInfo<>(shipAddresses);

            return ResponseBuilder.page(pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.pageFail(e.getMessage());
        }
    }

    private Response validate(ShipAddress shipAddress) {
        try {
            Assert.notNull(shipAddress, "参数不能为空！");
            Assert.hasText(shipAddress.getCode(), "编码不能为空！");
            Assert.hasText(shipAddress.getName(), "名称不能为空！");

            //电话号码验证
            Pattern phonePattern = Pattern.compile("1[35678]\\d-\\d{4}-\\d{4}");
            Matcher phoneMatcher = phonePattern.matcher(shipAddress.getMobilePhoneNo());
            Assert.isTrue(phoneMatcher.matches());

            return ResponseBuilder.success();
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}
