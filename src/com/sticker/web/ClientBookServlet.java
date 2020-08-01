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

        //设置page的部分请求地址
        page.setUrl("client/bookServlet?action=page");

        //3.将page对象数据保存到request域中
        req.setAttribute("page",page);
        //System.out.println("总页数：" + page.getPageTotal());

        //4.请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

    //pageByPrice
    //按照价格区间来查询，并将结果进行分页展示
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("首页请求转发来了！！！");

        //1.获取请求参数：当前页码和每页显示数量
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        // System.out.println("当前页码:"+pageNo);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //获取请求参数：价格区间的两个值
        int min = WebUtils.parseInt(req.getParameter("min"),0);
        int max = WebUtils.parseInt(req.getParameter("max"),Integer.MAX_VALUE);

        //2.BookService调用pageByPrice函数，进行持久化,获取page对象
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        //设置page的部分请求地址
        //page.setUrl("client/bookServlet?action=pageByPrice");

        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        // 如果有最小价格的参数,追加到分页条的地址参数中
        //这里是重新获取min和max的属性值，和上面的初始化的代码无关（即下面两行代码）
        //所以下面的非空判断还是需要的
        //注意下面两行代码只是给变量min和max有设置一个预备初值，而不是给input标签中的min和max属性赋予初值。
        //正是因为input标签中的min和max属性的值为null，防止空指针错误，以及最初展示效果，我们才给他一个预备初始值
//        int min = WebUtils.parseInt(req.getParameter("min"),0);
//        int max = WebUtils.parseInt(req.getParameter("max"),Integer.MAX_VALUE);

//        System.out.println("小值:"+req.getParameter("min"));
//        System.out.println("大值:"+req.getParameter("max"));

        if (req.getParameter("min") != null) {
            sb.append("&min=").append(req.getParameter("min"));
        }
        // 如果有最大价格的参数,追加到分页条的地址参数中
        if (req.getParameter("max") != null) {
            sb.append("&max=").append(req.getParameter("max"));
        }
        page.setUrl(sb.toString());


        //3.将page对象数据保存到request域中
        req.setAttribute("page",page);
        //System.out.println("总页数：" + page.getPageTotal());

        //4.请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

    protected void asd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
