package com.sticker.web;

import com.sticker.pojo.User;
import com.sticker.service.UserService;
import com.sticker.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //2.检查验证码是否正确  目前写死“qwert”
        if("qwert".equals(code)) {
            //正确的话，检查用户名是否可用
            if(userService.existsUsername(username)) {
                //用户名已存在，当前用户名不可用，跳回注册界面
                System.out.println("["+username+"]已存在！！！");

                //提示信息
                req.setAttribute("msg","用户名已存在！！！");
                //回显信息
                req.setAttribute("username",username);
                req.setAttribute("email",email);

                req.getRequestDispatcher("pages/user/regist.html").forward(req,resp);
            } else {
                //可用的话，调用Service保存到数据库，跳到注册成功界面
                userService.registUser(new User(null,username,password,email));
                req.getRequestDispatcher("pages/user/regist_success.html").forward(req,resp);
            }
        } else {
            //提示信息
            req.setAttribute("msg","验证码错误！！！");
            //回显信息
            req.setAttribute("username",username);
            req.setAttribute("email",email);


            //不正确的话，提示验证码错误，跳回注册界面
            System.out.println("[验证码"+code+"]错误！！！");
            req.getRequestDispatcher("pages/user/regist.html").forward(req,resp);
        }

    }
}
