package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopMapper {

    ShoppingCart selectOne(ShoppingCart shoppingCartQuery);

    void insert(ShoppingCart shoppingCart);

    void updateById(ShoppingCart shoppingCart);

    /*
     * 通过用户ID查看购物车
     * */
    List<ShoppingCart> selectByUserId(ShoppingCart shoppingCartQuery);


    /*
     * 删除购物车
     * */
    void deletByuserId(ShoppingCart shoppingCartQuery);

    /*
     * 删除一个
     * */
    void deleteOne(ShoppingCart shoppingCart);

    void insertBatch(List<ShoppingCart> shoppingCarts);

}
