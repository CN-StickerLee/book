package com.sticker.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//目的是为了代码复用，所以设置成abstract
public abstract class BaseServlet extends HttpServlet {

    //不用复制方法体，直接调用doPost，优雅就完事了。
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 解决post请求中文乱码问题
        // 一定要在获取请求参数之前调用才有效
        req.setCharacterEncoding("UTF-8");
        // 解决响应的中文乱码
        resp.setContentType("text/html; charset=UTF-8");

        String action = req.getParameter("action");

        //使用反射来替代if else
        try {
            Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);

            //这是啥呀:com.sticker.web.UserServlet@7fd861c6
           // System.out.println("这是啥呀:"+this);

            //因为这里UserServlet来调用了程序，所以this代表的就是UserServlet的一个实例
            //this代表UserServlet的实例
            method.invoke(this,req,resp);

            //System.out.println("这是啥呀:"+this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
