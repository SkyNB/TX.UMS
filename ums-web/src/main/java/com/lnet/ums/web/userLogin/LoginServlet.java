package com.lnet.ums.web.userLogin;

/**
 * Created by Wanj on 2017/1/17.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 处理登录的逻辑
 *
 */
@Controller
@RequestMapping("login")
public class LoginServlet  {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //1.接收参数
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");

        //2.判断逻辑
        if("eric".equals(userName)
                && "123456".equals(userPwd)){
            //登录成功
            /**
             * 分析：
             *    context域对象：不合适，可能会覆盖数据。
             *    request域对象： 不合适，整个网站必须得使用转发技术来跳转页面
             *    session域对象：合适。
             */
            /*
            request.setAttribute("loginName", userName);
            //request.getRequestDispatcher("/IndexServlet").forward(request, response);
            response.sendRedirect(request.getContextPath()+"/IndexServlet");
            */

            /**
             * 一、登录成功后，把用户数据保存session对象中
             */
            //1.创建session对象
            HttpSession session = request.getSession();
            //2.把数据保存到session域中
            session.setAttribute("loginName", userName);
            //3.跳转到用户主页
            response.sendRedirect(request.getContextPath()+"/IndexServlet");

        }else{
            //登录失败
            //请求重定向
            response.sendRedirect(request.getContextPath()+"/fail.html");
        }
    }

    @RequestMapping("w")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
