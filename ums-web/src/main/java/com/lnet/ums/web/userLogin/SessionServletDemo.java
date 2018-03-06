package com.lnet.ums.web.userLogin;

/**
 * Created by Wanj on 2017/1/17.
 */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 测试Session的方法
 */
@WebServlet(name = "SessionServletDemo")
public class SessionServletDemo extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.创建Session对象
        HttpSession session = request.getSession();

        //2.保存会话数据
        session.setAttribute("name","eric");

        //3.取出会话数据
        String name = (String)session.getAttribute("name");
        System.out.println(name);

        //4.
        //拿到Session的id
        System.out.println(session.getId());
        //修改Session有效时间
        session.setMaxInactiveInterval(20);
        //销毁session
        if (session!=null){
            session.invalidate();
        }
        //手动发送一个硬盘保存的cookie给浏览器
        Cookie c = new Cookie("JSESSION", session.getId());
        c.setMaxAge(60*60);
        response.addCookie(c);
    }
}