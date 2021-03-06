package com.sticker.web;

import com.google.gson.Gson;
import com.sticker.pojo.User;
import com.sticker.service.UserService;
import com.sticker.service.impl.UserServiceImpl;
import com.sticker.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //userService.login() 登录业务
        User loginUser = userService.login(new User(null, username, password, null));
        //2.调用userService处理login业务
        if(loginUser == null) {
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

            //req.getSession().setAttribute("username",username);
            // 保存用户登录的信息到Session域中
            req.getSession().setAttribute("user", loginUser);

            //如果登陆成功，跳到登陆成功界面
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //验证码相关
        // 获取 Session 中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //使用BeanUtils来优化代码
        User user = WebUtils.copyParamToBean(req.getParameterMap(),new User());

        //2.检查验证码是否正确
        if(token.equals(code)) {
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

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //销毁Session（或者销毁Session中用户登录信息）
        req.getSession().invalidate();
        //重定向到首页或者登录页面
        resp.sendRedirect(req.getContextPath());
    }

    protected void ajaxExistUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        String userName = req.getParameter("username");
        //检测用户名是否存在
        boolean existsUsername = userService.existsUsername(userName);
        //将返回的结果封装为Map对象
        Map<String,Object> map = new HashMap<>();
        map.put("existsUsername",existsUsername);
        //将map数据转为JSON字符串
        Gson gson = new Gson();
        String json = gson.toJson(map);
        //返回JSON字符串给AJAX
        resp.getWriter().write(json);
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
