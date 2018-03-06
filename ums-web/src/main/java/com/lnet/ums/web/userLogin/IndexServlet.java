package com.lnet.ums.web.userLogin;

/**
 * Created by Wanj on 2017/1/17.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户主页的逻辑
 *
 */
public class IndexServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();


        String html = "";

        /**
         * 接收request域对象的数据
         */
        /*
        String loginName = (String)request.getAttribute("loginName");
        */

        /**
         * 二、在用户主页，判断session不为空且存在指定的属性才视为登录成功！才能访问资源。
         * 从session域中获取会话数据
         */
        //1.得到session对象
        HttpSession session = request.getSession(false);
        if(session==null){
            //没有登录成功，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/login.html");
            return;
        }
        //2.取出会话数据
        String loginName = (String)session.getAttribute("loginName");
        if(loginName==null){
            //没有登录成功，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/login.html");
            return;
        }

        html = "<html><body>欢迎回来，"+loginName+"，<a href='"+request.getContextPath()+"/LogoutServlet'>安全退出</a></body></html>";


        writer.write(html);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
