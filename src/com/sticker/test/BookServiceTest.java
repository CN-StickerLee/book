package com.sticker.test;

import com.sticker.pojo.Book;
import com.sticker.service.BookService;
import com.sticker.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BookServiceTest {

    BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"是谁来了","手冢治系",
                new BigDecimal(123),999,111,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(22,"我来啦","手冢治系",
                new BigDecimal(99),1999,111,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(1));
    }

    @Test
    public void queryBooks() {
        System.out.println(bookService.queryBooks());
    }
}