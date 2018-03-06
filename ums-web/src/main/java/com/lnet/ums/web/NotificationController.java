package com.lnet.ums.web;

import com.lnet.framework.core.KendoGridRequest;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.SelectObject;
import com.lnet.model.ums.notification.notificationDto.NotificationDto;
import com.lnet.ums.contract.api.NotificationService;
import com.lnet.ums.contract.api.UserService;
import com.lnet.model.ums.user.User;
import com.lnet.ums.web.utils.SystemUtil;
import com.lnet.ums.web.utils.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notify")
public class NotificationController {
    private static final String systemCode = SystemUtil.SYS_CODE;

    @Resource
    private NotificationService notificationService;
    @Resource
    private UserService userService;
    @Autowired
    private UserPrincipalImpl userPrincipal;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "notify/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {
        List<User> users = userService.getAvailable(systemCode).getBody();
        List<SelectObject> selectObjects = users.stream().map(ele -> new SelectObject(ele.getUsername(), ele.getFullName())).collect(Collectors.toList());
        map.addAttribute("users", selectObjects);
        return "notify/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params) {
        return notificationService.send(params.get("subject").toString(), params.get("body").toString(), userPrincipal.getName(), (List<String>) params.get("receivers"));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(ModelMap map) {
        List<User> users = userService.getAvailable(systemCode).getBody();
        List<SelectObject> selectObjects = users.stream().map(ele -> new SelectObject(ele.getUsername(), ele.getFullName())).collect(Collectors.toList());
        map.addAttribute("users", selectObjects);
        return "notify/detail";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response detail(@PathVariable String id) {
        return notificationService.get(id);
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable String id, ModelMap map) {
        map.addAttribute("notificationDto", notificationService.get(id).getBody());
        return "notify/info";
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<NotificationDto> read(@PathVariable String id) {
        return notificationService.read(id, userPrincipal.getName());
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse<NotificationDto> search(@RequestBody KendoGridRequest params) {
        params.setParams("sender", userPrincipal.getName());
        params.addOrder("sentTime", "desc");
        return notificationService.pageList(params.getPage(), params.getPageSize(), params.getParams());
    }

    @RequestMapping(value = "/receiveNotify", method = RequestMethod.GET)
    public String receiveNotifyIndex() {
        return "notify/receiveNotifyIndex";
    }

    @RequestMapping(value = "/queryNotifyForReceive", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse<NotificationDto> queryNotifyForReceive(@RequestBody KendoGridRequest params) {
        params.setParams("receiver", userPrincipal.getName());
//        params.addOrder("read", "desc");
        params.addOrder("sentTime", "desc");
        return notificationService.queryForReceive(params.getPage(), params.getPageSize(), params.getParams());
    }

    @RequestMapping(value = "/recall/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response recall(@PathVariable String id) {
        return notificationService.recall(id);
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read() {
        return "notify/read";
    }
}
