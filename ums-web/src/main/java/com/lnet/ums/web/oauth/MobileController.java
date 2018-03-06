package com.lnet.ums.web.oauth;

import com.lnet.ums.contract.model.oauth.UserJsonDto;
import com.lnet.ums.contract.oauth.OauthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lxf
 */
@Controller
@RequestMapping("/m/")
public class MobileController {

    @Autowired
    private OauthUserService userService;


    @RequestMapping("dashboard")
    public String dashboard() {
        return "mobile/dashboard";
    }

    @RequestMapping("user_info")
    @ResponseBody
    public UserJsonDto userInfo() {
        return userService.loadCurrentUserJsonDto();
    }

}