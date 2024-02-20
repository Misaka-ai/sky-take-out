package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

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

    /*
     * 根据ID查询套餐
     * */
    SetmealVO getSetmeal(Long id);

    /*
     * 根据ID批量删除套餐
     * */
    void deleteByids(List<Long> ids);

    /*
     * 停售起售套餐
     * */
    void setStatus(Long id, Integer status);

    /*
     * 修改套餐
     * */
    void updateSetmeal(SetmealDTO setmealDTO);
}
