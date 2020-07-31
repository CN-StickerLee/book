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
import java.util.List;

public class BookServlet extends BaseServlet {

    BookService bookService = new BookServiceImpl();

    /**
     * 展示图书列表
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    //方法名要和页面标签中属性action的值对应，注意大小写
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.通过BookService 查询全部图书
        List<Book> books = bookService.queryBooks();
        //2.把全部图书保存到Request域中
        req.setAttribute("books",books);
        //3.请求转发到/pages/manager/book_manager.jsp 页面
        //请求转发是一次请求，只有一个请求域。
        //重定向是两次请求，有两个请求域。
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    /**
     * 添加图书,在图书编辑界面调用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        注意：添加的时候页码数加1，这样如果本来就需要增加页数的话可以跳转到最后一页，不要
//        增加页数的话，由于页码大于总页数，我们前面加了限制，也会重新被赋值，跳转到最后一页。
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),0);
        pageNo += 1;

        System.out.println("测试也一样晕晕晕："+pageNo);

        //1.获取请求参数，封装成Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());

        //2.BookService调用addBook函数，进行持久化
        bookService.addBook(book);

        //3.重定向跳转到图书列表页面
        //这里不能用请求转发，因为请求转发是一次请求，在这里包含
        //添加图书和展示所有图书两个动作，而应该用重定向，重定向是
        //两次请求。
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page" +
                "&pageNo="+ pageNo);
    }

    /**
     * 删除图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数id

        //这里涉及到一个String到Integer的类型转换，因为会抛异常，所以定义了一个方法来处理
        //为了防止出现空指针的错误以及可能传过来不是数字的字符串，给了一个不属于图书编号的默认值0
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2.BookService调用deleteBookById函数，进行持久化
        bookService.deleteBookById(id);
        //3.重定向跳转到图书列表页面
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+req.getParameter("pageNo"));
    }

    /**
     * 点击图书的修改按钮时需要调用此函数，获取图书，并转到编辑图书界面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取需要修改的图书ID
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2.调用bookService的queryBookById方法
        Book book = bookService.queryBookById(id);
        //3.将该图书信息保存到request域中
        req.setAttribute("book",book);
        //4.请求转发到 pages/manager/book_edit.jsp 页面
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }

    /**
     * 更新图书信息,在图书编辑界面调用     修改图书更新图书的信息后，需要跳转到图书的展示界面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求参数，封装成Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());

        //2.BookService调用updateBook函数，进行持久化
        bookService.updateBook(book);

        //3.重定向跳转到图书列表页面
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+req.getParameter("pageNo"));;
    }

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    protected void bak(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求参数，封装成Book对象

        //2.BookService调用updateBook函数，进行持久化

        //3.重定向跳转到图书列表页面

    }

}
