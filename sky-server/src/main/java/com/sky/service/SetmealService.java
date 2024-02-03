package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

public interface SetmealService {

    /*
     *
     * 分页查询套餐
     * */
    PageResult selcectAllSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    /*
     * 添加套餐
     * */
    void addSetmeal(SetmealDTO setmealDTO);
}
