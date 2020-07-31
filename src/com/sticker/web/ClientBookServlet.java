package com.sticker.web;

import com.sticker.pojo.Book;
import com.sticker.pojo.Page;
import com.sticker.service.BookService;
import com.sticker.service.impl.BookServiceImpl;
import com.sticker.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientBookServlet extends BaseServlet {

    BookService bookService = new BookServiceImpl();

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //System.out.println("首页请求转发来了！！！");

        //1.获取请求参数：当前页码和每页显示数量
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        // System.out.println("当前页码:"+pageNo);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //2.BookService调用page函数，进行持久化,获取page对象
        Page<Book> page = bookService.page(pageNo,pageSize);

        //3.将page对象数据保存到request域中
        req.setAttribute("page",page);
        //System.out.println("总页数：" + page.getPageTotal());

        //4.请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

}
