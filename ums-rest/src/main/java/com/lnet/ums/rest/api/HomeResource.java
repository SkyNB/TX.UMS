package com.lnet.ums.rest.api;

//import com.lnet.microservices.infrastructure.domain.user.User;
//import com.lnet.microservices.infrastructure.spi.UserService;
//import com.lnet.microservices.orm.domain.LogisticsOrder;
import com.lnet.model.ums.transprotation.transprotationEntity.LogisticsOrder;
import com.lnet.model.ums.user.User;
import com.lnet.ums.contract.api.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/23.
 */
@RestController
@RequestMapping("/")
public class HomeResource {

    @Resource
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Object home() {
        return "success";
    }
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Object get(@PathVariable String username) {
        User user =userService.getByUserName(username).getBody();
//        user.setType(null);
        return user;
    }
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/json")
    public Object test() {
        Map<String, Object> result = new HashMap<>();
        result.put("date", new Date());
        result.put("localDate", LocalDate.now());
        result.put("localDateTime", LocalDateTime.now());
        result.put("orderStatus", LogisticsOrder.Status.values());
        return result;
    }
    @RequestMapping(value = "test.xml", method = RequestMethod.GET, produces = "application/xml")
    public Object testXML()  {
        Map<String, Object> result = new HashMap<>();
        result.put("date", new Date());
        result.put("localDate", LocalDate.now());
        result.put("localDateTime", LocalDateTime.now());
        return result;
    }
}
