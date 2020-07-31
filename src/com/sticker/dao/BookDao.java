package com.sticker.dao;

import com.sticker.pojo.Book;

import java.util.List;

public interface BookDao {

    //定义需要进行的与书籍操作相关的CRUD方法

    public int addBook(Book book);             //增

    public int deleteBookById(Integer id);     //删

    public int updateBook(Book book);          //改

    public Book queryBookById(Integer id);     //查

    public List<Book> queryBooks();            //查

    public int queryForItemsTotalCount();        //获取总记录数

    public List<Book> queryForPageItems(int begin, int pageSize);             //获取当前页面的记录

    public int queryForItemsTotalCountByPrice(int min, int max);        //获取总记录数

    public List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);             //获取当前页面的记录
}
