package com.sticker.dao;

import com.sticker.pojo.User;

public interface UserDao {
    User queryUserByUserame(String name);

    User queryUserByUsernameAndPassword(String username,String password);

    int saveUser(User user);
}
