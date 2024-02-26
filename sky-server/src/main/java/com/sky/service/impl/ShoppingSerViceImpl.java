package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShopMapper;
import com.sky.service.ShoppingSerVice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShoppingSerViceImpl implements ShoppingSerVice {
    private final ShopMapper shopMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    @Override
    public void addShopping(ShoppingCartDTO shoppingCartDTO) {


        Long id = BaseContext.getCurrentId();
        //类型为菜品
        if (Objects.nonNull(shoppingCartDTO.getDishId())) {
            //为空则添加到购物车中
            ShoppingCart shoppingCartQuery = new ShoppingCart();
            shoppingCartQuery.setDishId(shoppingCartDTO.getDishId());
            shoppingCartQuery.setDishFlavor(shoppingCartDTO.getDishFlavor());
            //查询
            ShoppingCart shoppingCart = shopMapper.selectOne(shoppingCartQuery);

            if (Objects.isNull(shoppingCart)) {
                //菜品
                Dish dish = dishMapper.selectById(shoppingCartDTO.getDishId());
                shoppingCart = new ShoppingCart();
                shoppingCart.setName(dish.getName());
                shoppingCart.setUserId(id);
                shoppingCart.setDishId(shoppingCartDTO.getDishId());
                shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setCreateTime(LocalDateTime.now());
                shopMapper.insert(shoppingCart);

            } else {
                shoppingCart.setNumber(shoppingCart.getNumber() + 1);
                shopMapper.updateById(shoppingCart);
            }
        }

        //套餐
        if (Objects.nonNull(shoppingCartDTO.getSetmealId())) {


            ShoppingCart shoppingCartQuery = new ShoppingCart();
            shoppingCartQuery.setSetmealId(shoppingCartDTO.getSetmealId());
            //查询
            ShoppingCart shoppingCart = shopMapper.selectOne(shoppingCartQuery);

            if (Objects.isNull(shoppingCart)) {
                //菜品
                Setmeal setmeal = setmealMapper.selectById(shoppingCartDTO.getSetmealId());
                shoppingCart = new ShoppingCart();
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setUserId(id);
                shoppingCart.setDishId(shoppingCartDTO.getSetmealId());

                shoppingCart.setNumber(1);
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setCreateTime(LocalDateTime.now());
                shopMapper.insert(shoppingCart);

            } else {
                shoppingCart.setNumber(shoppingCart.getNumber() + 1);
                shopMapper.updateById(shoppingCart);
            }
        }
    }

    @Override
    public List<ShoppingCart> getShoppings(Long userId) {
        ShoppingCart shoppingCartQuery = new ShoppingCart();
        shoppingCartQuery.setUserId(userId);
        List<ShoppingCart> shoppingCarts = shopMapper.selectByUserId(shoppingCartQuery);

        return shoppingCarts;
    }

    @Override
    public void deleteByid(Long userId) {
        ShoppingCart shoppingCartQuery = new ShoppingCart();
        shoppingCartQuery.setUserId(userId);
        shopMapper.deletByuserId(shoppingCartQuery);
    }

    @Override
    public void deletOneByUserId(ShoppingCartDTO shoppingCartDTO) {

        if (Objects.nonNull(shoppingCartDTO.getDishId())) {
            //获取当前用户ID
            Long userId = BaseContext.getCurrentId();
            ShoppingCart shoppingCartQuery = new ShoppingCart();
            shoppingCartQuery.setUserId(userId);
            shoppingCartQuery.setDishId(shoppingCartDTO.getDishId());
            //更具当前这个条件查出当前菜品的数量
            ShoppingCart shoppingCart = shopMapper.selectOne(shoppingCartQuery);
            if (shoppingCart != null) {
                //判断菜品的数量
                if (shoppingCart.getNumber() > 1) {
                    shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                    shopMapper.updateById(shoppingCart);
                } else {
                  //删除当前菜品购物车记录
                    shopMapper.deleteOne(shoppingCart);
                }
            }




        }

        if (Objects.nonNull(shoppingCartDTO.getSetmealId())) {
            //获取当前用户ID
            Long userId = BaseContext.getCurrentId();
            ShoppingCart shoppingCartQuery = new ShoppingCart();
            shoppingCartQuery.setUserId(userId);
            shoppingCartQuery.setDishId(shoppingCartDTO.getDishId());
            //更具当前这个条件查出当前菜品的数量
            ShoppingCart shoppingCart = shopMapper.selectOne(shoppingCartQuery);
            if (shoppingCart != null) {
                //判断菜品的数量
                if (shoppingCart.getNumber() > 1) {
                    shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                    shopMapper.updateById(shoppingCart);
                } else {
                    //删除当前菜品购物车记录
                    shopMapper.deleteOne(shoppingCart);
                }
            }


        }
    }
}
