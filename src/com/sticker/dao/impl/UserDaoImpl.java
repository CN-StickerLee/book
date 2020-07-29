package com.sticker.dao.impl;

import com.sticker.dao.UserDao;
import com.sticker.pojo.User;

public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public User queryUserByUsername(String username) {
        String sql = "select `id`,`username`,`password`,`email` from t_user where username = ?";
        return queryForOne(User.class,sql,username);
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        //这里要注意换行的时候，一定要有空格，否则关键字连在一起，sql语句是错误的。
        String sql = "select `id`,`username`,`password`,`email` from t_user where username = ? and" +
                " password = ?";
        return queryForOne(User.class,sql,username,password);
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(`username`,`password`,`email`) values(?,?,?)";
        return update(sql,user.getUsername(),user.getPassword(),user.getEmail());
    }
}
