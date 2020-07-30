package com.sticker.service;

import com.sticker.pojo.Book;
import com.sticker.pojo.Page;

import java.util.List;

public interface BookService {
    //利用前面的增删改查函数组合来实现业务

    //这里不需要设置返回值，因为如果报错的话，DAO层函数可以输出返回值
    public void addBook(Book book);

    public void deleteBookById(Integer id);

    public void updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    public Page<Book> page(int pageNo, int pageSize);
}
