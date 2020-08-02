package com.sticker.service;

import com.sticker.pojo.Cart;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
}
