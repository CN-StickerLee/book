package com.sticker.web;

import com.sticker.pojo.User;
import com.sticker.service.UserService;
import com.sticker.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //2.调用userService处理login业务
        if(userService.login(new User(null,username,password,null)) == null) {
            //如果等于null，说明登录失败，跳回登录界面
            System.out.println("登陆失败！！！");

            //登陆失败的提示
            req.setAttribute("msg","用户名或密码错误！！！");
            //回显的信息
            req.setAttribute("username",username);

            //这里只是请求转发到相应的网页文件，浏览器上的请求路径和这里没有关系，不会因为下面的代码改变
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        } else {
            System.out.println("登陆成功！！！");
            //如果登陆成功，跳到登陆成功界面
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
    }
}
