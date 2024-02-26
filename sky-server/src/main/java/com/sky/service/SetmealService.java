package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * SetmealService
 *
 * @author liliudong
 * @version 1.0
 * @description
 * @date 2023/7/31 18:52
 */
public interface SetmealService {

    /**
     * 按类别id列表
     *
     * @param categoryId 类别id
     * @return {@link List}<{@link Setmeal}>
     */
    List<Setmeal> listByCategoryId(Integer categoryId);

    /**
     * 盘通过id列表
     *
     * @param setmealId setmeal id
     * @return {@link List}<{@link Dish}>
     */
    List<SetmealDish> listDishById(Long setmealId);
    /**
     * 创建
     *
     * @param setmealDTO setmeal dto
     */
    void create(SetmealDTO setmealDTO);

    /**
     * 删除由ids
     *
     * @param ids id
     */
    void deleteByIds(List<Long> ids);

    /**
     * 更新通过id
     *
     * @param setmealDTO setmeal dto
     */
    void updateById(SetmealDTO setmealDTO);

    /**
     * 更新状态通过id
     *
     * @param id     id
     * @param status 状态
     */
    void updateStatusById(Long id, Integer status);

    /**
     * 页面
     *
     * @param setmealPageQueryDTO setmeal页面查询dto
     * @return {@link PageResult}
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 查询通过id
     *
     * @param id id
     * @return {@link SetmealVO}
     */
    SetmealVO queryById(Long id);
}
