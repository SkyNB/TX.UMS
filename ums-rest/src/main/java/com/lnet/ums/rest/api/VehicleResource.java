package com.lnet.ums.rest.api;

import com.lnet.framework.core.Response;
import com.lnet.model.ums.vehicle.vehicleDto.VehicleListDto;
import com.lnet.ums.rest.api.Impl.VehicleApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
@RestController
@RequestMapping("vehicle")
public class VehicleResource {

    @Resource
    VehicleApplication vehicleApplication;

    @GetMapping("getBranchCodeLe/{branchCode}")
    public Response<List<VehicleListDto>> getBranchCodeLe(@PathVariable String branchCode) {
        return vehicleApplication.getBranchIdle(branchCode);
    }

    @GetMapping("getByUserId/{userId}")
    public Response<VehicleListDto> getByUserId(@PathVariable String userId) {
        return vehicleApplication.getByUserId(userId);
    }
}
