package com.sticker.service.impl;

import com.sticker.dao.BookDao;
import com.sticker.dao.impl.BookDaoImpl;
import com.sticker.pojo.Book;
import com.sticker.pojo.Page;
import com.sticker.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }


    //填充一个分页对象中的数据
    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> bookPage = new Page<>();


        // 设置每页显示数量
        bookPage.setPageSize(pageSize);
        // 总记录数
        Integer totalCount = bookDao.queryForItemsTotalCount();
        //设置总记录数
        bookPage.setPageTotalCount(totalCount);

        // 总页码
        Integer pageTotal = totalCount / pageSize;
        if (totalCount % pageSize > 0 ) {
            pageTotal += 1;
        }
        //设置总页数
        bookPage.setPageTotal(pageTotal);
        System.out.println("总页码："+pageTotal);

        //因为为了防止不合法的页码输入，所以要进行页码和0以及当前总页数的比较
        //因此设置当前页码需要放在获取设置总页码的代码后面

//        /* 数据边界的有效检查 */
//        if (pageNo < 1) {
//            pageNo = 1;
//        }
//        if (pageNo > pageTotal) {
//            pageNo = pageTotal;
//        }

        //为了通用性，上面的判断直接写到了类对象的set函数中
        // 设置当前页码
        bookPage.setPageNo(pageNo);

        //当前页的开始索引
        int begin  = (bookPage.getPageNo() - 1) * bookPage.getPageSize();
        // 当前页数据
        List<Book> bookList = bookDao.queryForPageItems(begin,pageSize);

        //设置当前页的展示书列表
        bookPage.setItems(bookList);

        return bookPage;
    }


}
