package com.sky.service;

import com.sky.entity.Category;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.UserLoginVO;

import java.util.List;

public interface UserService {
    /*
     * 用户登录
     *
     * */
    UserLoginVO userLogin(String code);

    /*
     * 套餐菜品查询
     * */
    List<Category> getCategorys(Integer type);

    /*
     * 根据分类ID查询菜品
     * */
    List<DishVO> getDishs(Integer categoryId);

    /*
     *
     *根据分类id查询套餐
     * */
    List<Category> getCategory(Integer categoryId);
   
}
