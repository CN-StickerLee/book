package com.sticker.web;

import com.sticker.pojo.User;
import com.sticker.service.UserService;
import com.sticker.service.impl.UserServiceImpl;
import com.sticker.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //使用BeanUtils来优化代码
        User user = WebUtils.copyParamToBean(req.getParameterMap(),new User());

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

                req.getRequestDispatcher("pages/user/regist.jsp").forward(req,resp);
            } else {
                //可用的话，调用Service保存到数据库，跳到注册成功界面
                userService.registUser(user);
                req.getRequestDispatcher("pages/user/regist_success.jsp").forward(req,resp);
            }
        } else {
            //提示信息
            req.setAttribute("msg","验证码错误！！！");
            //回显信息
            req.setAttribute("username",username);
            req.setAttribute("email",email);


            //不正确的话，提示验证码错误，跳回注册界面
            System.out.println("[验证码"+code+"]错误！！！");
            req.getRequestDispatcher("pages/user/regist.jsp").forward(req,resp);
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req,resp);
////        String action = req.getParmeter("action");
////        if("login".equals(regType)) {
////            login(req,resp);
////        } else if("regist".equals(regType)) {
////            regist(req,resp);
////        }
//    }
}
