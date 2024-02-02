package com.sky.service;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

public interface SetmealService {

    /*
     *
     * 分页查询
     * */
    PageResult selcectAllSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);
}
