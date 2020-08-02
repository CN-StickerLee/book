package com.sticker.web;

import com.sticker.pojo.Book;
import com.sticker.pojo.Cart;
import com.sticker.pojo.CartItem;
import com.sticker.service.BookService;
import com.sticker.service.impl.BookServiceImpl;
import com.sticker.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartServlet extends BaseServlet {

    //不能放在这里，应该放在添加商品里面(看一下这里行不行)
    //Cart cart = new Cart();
    BookService bookService = new BookServiceImpl();

    //加入购物车
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取相关参数:商品ID
        int  bookId = WebUtils.parseInt(req.getParameter("id"),0);

        //根据id获得Book对象，进而得到CartItem对象
        Book book = bookService.queryBookById(bookId);
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());

        //将购物车信息保存
        //将商品加入购物车
        //注意这里需要使用session域来存储，不能使用request域，因为request域只在一次请求有效
        //而下面使用的是重定向，是两次请求，为什么使用重定向呢？
        //因为防止浏览器缓存，进而刷新页面造成加入购物车操作重复提交
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //首次创建Cart
        if(cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }

        cart.addItem(cartItem);

        //获取refer信息，进而可以得到原来商品所在的页面(请求前的主页地址)，从而重定向到主页
        //System.out.println(cart);
        //System.out.println("请求头中Referer的值：" + req.getHeader("Referer"));

        resp.sendRedirect(req.getHeader("Referer"));
    }


    //删除购物车的商品项
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("删除商品项");

        //获取相关参数:商品ID
        int  bookId = WebUtils.parseInt(req.getParameter("id"),0);

        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart != null) {
            //删除商品项
            cart.deleteItem(bookId);
            //重定向到原来的购物车界面
            resp.sendRedirect(req.getHeader("Referer"));
        }

    }
    //清空购物车 cleanCart
    protected void cleanCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("清空购物车");

        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        //清空购物车
        if(cart != null) {
            cart.clear();
            //重定向回购物车界面
            resp.sendRedirect(req.getHeader("Referer"));
        }

    }
    //修改购物车的商品数量

}
