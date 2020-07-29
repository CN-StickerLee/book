package com.sticker.test;

import com.sticker.dao.UserDao;
import com.sticker.dao.impl.UserDaoImpl;
import com.sticker.pojo.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoTest {

    //默认存在一个无参构造函数（如果不定义有参的构造函数的话）
    UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {
        if (userDao.queryUserByUsername("admin1234") == null) {
            System.out.println("当前用户名可用！！！");
        } else {
            System.out.println("对不起，当前用户名不可用！！！");
        }
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        if (userDao.queryUserByUsernameAndPassword("admin","admin") == null) {
            System.out.println("当前用户名或密码错误，请检查！！！");
        } else {
            System.out.println("用户名信息正确，可登录！！！");
        }

    }

    @Test
    public void saveUser() {
        System.out.println(userDao.saveUser(new User(null,"zxcvbn","123456","zxcvbn@qq.com")));
    }
}