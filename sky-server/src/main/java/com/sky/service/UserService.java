package com.sky.service;

import com.sky.entity.Category;
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
    List<Category> getCategorys();
}
