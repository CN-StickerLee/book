package com.sticker.dao.impl;

import com.sticker.dao.BookDao;
import com.sticker.dao.UserDao;
import com.sticker.pojo.Book;
import com.sticker.pojo.User;

import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {

    //实现DAO层定义的增删改查方法，基本套路：
    //1）定义sql语句；
    //2) 调用BaseDao的封装方法
    @Override
    public int addBook(Book book) {

        //因为id作为主键，一般是由数据库自己取递增自动生成的，所以不需要我们自己去赋值
        //sql语句最后的分号 ; 可以不写，不影响结果
        //换行不能少了空格，否则sql语句是错误的
        String sql = "insert into t_book(`name` , `author` , `price` , `sales` , `stock` , `img_path`)" +
                " values(? , ? , ? , ? , ? , ?)";

        //在数据库中，如果不涉及返回数据库中数据的语句的话，一般会返回受影响的行数。
        return update(sql,
        //注意这里要和上面insert语句中的字段对应，否则可能会出现错误。
        book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath());
    }

    @Override
    public int deleteBookById(Integer id) {

        String sql = "delete from t_book where id = ?";
        return update(sql,id);
    }

    /**
     *
     * @param book
     * @return
     */
    @Override
    public int updateBook(Book book) {

        String sql = "update t_book set `name`=?,`author`=?,`price`=?,`sales`=?,`stock`=?,`img_path`=? where `id`=?";
        return update(sql,
                book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath(),book.getId());
    }

    @Override
    public Book queryBookById(Integer id) {
        //这里为什么要取别名？是因为各个字段要和JavaBean的成员变量对应
        String sql = "select `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath" +
                //换行不能少了空格，否则sql语句是错误的
                " from t_book where id = ?";
        return queryForOne(Book.class,sql,id);
    }

    @Override
    public List<Book> queryBooks() {
        String sql = "select `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath" +
                " from t_book";
        return queryForList(Book.class,sql);
    }

    @Override
    public int queryForItemsTotalCount() {
        String sql = "select count(*) from t_book";
        Number count = (Number)queryForSingleValue(sql);
        //intValue方法将Number类型转换为int型数据
        return count.intValue();
    }

    @Override
    public List<Book> queryForPageItems(int begin,int pageSize) {
        String sql = "select `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath" +
                " from t_book limit ?,?";

        return queryForList(Book.class,sql,begin,pageSize);
    }

    @Override
    public int queryForItemsTotalCountByPrice(int min, int max) {
        String sql = "select count(*) from t_book where price between ? and ?";
        Number count = (Number)queryForSingleValue(sql,min,max);
        //intValue方法将Number类型转换为int型数据
        return count.intValue();
    }

    @Override
    public List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max) {
        String sql = "select `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath" +
                " from t_book where price between ? and ? order by price limit ?,?";
        //注意下面最后的几个参数是和sql语句中的参数位置一一对应的，而不是和queryForPageItemsByPrice函数参数的顺序对应
        return queryForList(Book.class,sql,min,max,begin,pageSize);
    }
}
