package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishService {
    /*
     * 分页查询菜品
     * */
    PageResult pageDish(DishPageQueryDTO dishPageQueryDTO);

    /*
     * 修改菜品
     * */
    void updateDish(DishDTO dishDTO);

    /*
     * 根据ID查询菜品
     * */
    DishVO getDish(Long id);
}
