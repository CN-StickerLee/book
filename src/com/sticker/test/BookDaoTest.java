package com.sticker.test;

import com.sticker.dao.BookDao;
import com.sticker.dao.impl.BookDaoImpl;
import com.sticker.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BookDaoTest {

    private BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {

        bookDao.addBook(new Book(null,"是谁来了","手冢治系",
                new BigDecimal(123),999,111,null));

    }

    @Test
    public void deleteBookById() {

        bookDao.deleteBookById(21);

    }

    @Test
    public void updateBook() {

        bookDao.updateBook(new Book(21,"我来啦","手冢治系",
                new BigDecimal(99),1999,111,null));
    }

    @Test
    public void queryBookById() {

        System.out.println(bookDao.queryBookById(20));

    }

    @Test
    public void queryBooks() {

       // System.out.println(bookDao.queryBooks());

        for (Book queryBook:
             bookDao.queryBooks()) {
            System.out.println(queryBook);
        }
    }

}