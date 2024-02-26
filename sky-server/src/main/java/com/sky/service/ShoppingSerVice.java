package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingSerVice {
    void addShopping(ShoppingCartDTO shoppingCartDTO);

    /*
     * 通过用户ID查看购物车
     * */
    List<ShoppingCart> getShoppings(Long userId);
    /*
     * 清空当前用户的购物车
     *
     * */
    void deleteByid(Long userId);
    /*
     * 减少购物车中的数量
     * */
    void deletOneByUserId(ShoppingCartDTO shoppingCartDTO);
}
