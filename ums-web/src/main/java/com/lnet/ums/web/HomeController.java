package com.lnet.ums.web;

import com.lnet.model.ums.ErpUser;
import com.lnet.ums.contract.api.ErpBaseMenuService;
import com.lnet.ums.contract.api.UserContract;
import com.lnet.model.ums.po.ErpBaseMenuCtPo;
import com.lnet.ums.web.utils.FtpUtils;
import com.lnet.ums.web.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/30.
 */

@RequestMapping("/")
@Controller
public class HomeController {

    @Resource
    UserContract userContract;

    @Resource
    ErpBaseMenuService erpBaseMenuService;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(){

        System.out.println("跳转到登录页面...");

        return "login";
    }


    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(ModelMap map,HttpServletRequest request) {
        HttpSession session = request.getSession();
        ErpUser erpUser = (ErpUser)session.getAttribute("erpuser");
        if(erpUser!=null){
            List<ErpBaseMenuCtPo> erpBaseMenuCtsList=erpBaseMenuService.selectErpBaseMenuList(erpUser.getUserId());
            if(erpBaseMenuCtsList!=null&&erpBaseMenuCtsList.size()>0){
                map.put("userData", JsonUtils.toJson(erpBaseMenuCtsList));
                map.put("ErpUser",erpUser);
                return "frameLayouts/horizontal_layout";
            }
        }
        return "login";

          /* HttpSession session = request.getSession();
        ErpUser ErpUser = new ErpUser();//(ErpUser)session.getAttribute("erpuser");
        // TODO: 2017/2/3
        ErpUser.setUserId("1051612010948041791");
        if(ErpUser!=null){
            // TODO: 2017/2/3 待修改
            List<ErpBaseMenuCtPo> erpBaseMenuCtsList=erpBaseMenuService.selectErpBaseMenuList("1051612010948041791");//(ErpUser.getUserId());
            if(erpBaseMenuCtsList!=null&&erpBaseMenuCtsList.size()>0){
//                String baseJson= JsonUtils.toJson(erpBaseMenuCtsList);
//                map.put("ErpUserName",ErpUser.getUserName());
//                map.put("realname",ErpUser.getRealName());
                map.put("userData", JsonUtils.toJson(erpBaseMenuCtsList));
                map.put("ErpUser",ErpUser);
                return "umsText/index";
            }
        }
        return "login";*/
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(String userName, String passWord, boolean isRemember, ModelMap map,HttpServletRequest request) {

        try {
            String userpwd= FtpUtils.MD5(passWord);
            Map<String,String> maps = new HashMap<String,String>();
            maps.put("userName",userName);
            maps.put("userPwd",userpwd);
            if(maps!=null&&maps.size()>0) {
                ErpUser erpuser = userContract.selectErpUser(maps);
                if(erpuser!=null){
                    HttpSession session = request.getSession();
                    System.out.print("###########################"+session.getId()+"###########################");
                    session.setAttribute("erpuser",erpuser);
                    return "redirect:index";
                }
            }
        }catch (Exception e){
            e.getLocalizedMessage();
            return "common/error";
        }
        map.addAttribute("", userName);
        return "login";


        /*UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(isRemember);
        try {
            SecurityUtils.getSubject().login(token);
            return "redirect:/";
        } catch (UnknownAccountException uae) {
            map.addAttribute("message", "用户名不存在");
        } catch (IncorrectCredentialsException ice) {
            map.addAttribute("message", "密码不正确！");
        } catch (LockedAccountException lae) {
            map.addAttribute("message", "用户被禁用！");
        } catch (AuthenticationException ae) {
            map.addAttribute("message", "登录失败！");
        } catch (Exception ae) {
            map.addAttribute("message", "登录失败！");
        }

        map.addAttribute("", username);
        return "login";*/
    }

    @RequestMapping(value = "loginOut", method = RequestMethod.GET)
    public String loginOut() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }
    //----以下代码修改:刘文龙 2017年2月8日11:01:28
    @RequestMapping(value = "horizontalLayout", method = RequestMethod.GET)
    public String horizontalLayout(ModelMap map,HttpServletRequest request) {//横向布局
        HttpSession session = request.getSession();
        ErpUser erpUser = (ErpUser)session.getAttribute("erpuser");
        if(erpUser!=null){
            List<ErpBaseMenuCtPo> erpBaseMenuCtsList=erpBaseMenuService.selectErpBaseMenuList(erpUser.getUserId());
            if(erpBaseMenuCtsList!=null&&erpBaseMenuCtsList.size()>0){
                map.put("userData", JsonUtils.toJson(erpBaseMenuCtsList));
                map.put("erpUserCt",erpUser);
                return "frameLayouts/horizontal_layout";
            }
        }
        return "login";
/*
        HttpSession session = request.getSession();
        ErpUser erpUser = new ErpUser();//(ErpUserCt)session.getAttribute("erpuser");
        // TODO: 2017/2/3
        erpUser.setUserId("1051612010948041791");
        if (erpUser != null) {
            // TODO: 2017/2/3 待修改
            List<ErpBaseMenuCtPo> erpBaseMenuCtsList = erpBaseMenuService.selectErpBaseMenuList("1051612010948041791");//(erpUserCt.getUserId());
            if (erpBaseMenuCtsList != null && erpBaseMenuCtsList.size() > 0) {
                map.put("userData", JsonUtils.toJson(erpBaseMenuCtsList));
                map.put("erpUserCt", erpUser);
                return "umsText/horizontal_layout";
            }
        }
        return "login";*/
    }
    @RequestMapping(value = "verticalLayout", method = RequestMethod.GET)
    public String verticalLayout(ModelMap map,HttpServletRequest request) {//纵向布局
        HttpSession session = request.getSession();
        ErpUser erpUser = new ErpUser();//(ErpUserCt)session.getAttribute("erpuser");
        // TODO: 2017/2/3
        erpUser.setUserId("1051612010948041791");
        if (erpUser != null) {
            // TODO: 2017/2/3 待修改
            List<ErpBaseMenuCtPo> erpBaseMenuCtsList = erpBaseMenuService.selectErpBaseMenuList("1051612010948041791");//(erpUserCt.getUserId());
            if (erpBaseMenuCtsList != null && erpBaseMenuCtsList.size() > 0) {
                map.put("userData", JsonUtils.toJson(erpBaseMenuCtsList));
                map.put("erpUserCt", erpUser);
                return "frameLayouts/vertical_layout";
            }
        }
        return "login";
    }

}
