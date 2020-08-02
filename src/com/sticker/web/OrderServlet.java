package com.sticker.web;

import com.sticker.pojo.Cart;
import com.sticker.pojo.User;
import com.sticker.service.OrderService;
import com.sticker.service.impl.OrderServiceImpl;
import com.sticker.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 先获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        // 获取Userid
        User loginUser = (User) req.getSession().getAttribute("user");

        //用户没有登录的话，不能结账，让他通过请求转发跳转到登录页面。
        if (loginUser == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }

        System.out.println("OrderServlet程序在[" +Thread.currentThread().getName() + "]中");

        Integer userId = loginUser.getId();
//        调用orderService.createOrder(Cart,Userid);生成订单

        String orderId = null;

        //try {
            orderId = orderService.createOrder(cart, userId);
       //     JdbcUtils.commitAndClose(); //数据库DAO操作等整个流程没有异常的话，提交事务
       // } catch (Exception e) {
       //     JdbcUtils.rollbackAndClose();  //整个流程出现异常的话，回滚事务
      //      e.printStackTrace();  //打印异常信息
       // }

//        req.setAttribute("orderId", orderId);
        // 请求转发到/pages/cart/checkout.jsp
//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req, resp);

        req.getSession().setAttribute("orderId",orderId);

        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }

}
