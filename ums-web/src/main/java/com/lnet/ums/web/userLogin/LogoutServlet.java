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

/**
 * 退出逻辑
 *
 */
public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 三、安全退出：
         *      删除掉session对象中指定的loginName属性即可！
         */
        //1.得到session对象
        HttpSession session = request.getSession(false);
        if(session!=null){
            //2.删除属性
            session.removeAttribute("loginName");
        }

        //2.回来登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
