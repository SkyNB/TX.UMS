package com.lnet.ums.web.oauth;

import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 123456 on 2017/2/3.
 * @author lxf
 */
@Controller
@RequestMapping("/oauthLogin/")
public class OauthLoginController {

    @RequestMapping(value = "userLogin")
    public String userLogin(@RequestParam String authentication_error){



        System.out.println(authentication_error);

        System.out.println("用户登录验证。。。");

        return "redirect:/index";
    }
/*29f6004fb1b0466f9572b02bf2ac1be8
e10adc3949ba59abbe56e057f20f883e
21232F297A57A5A743894A0E4A801FC3*/
}
