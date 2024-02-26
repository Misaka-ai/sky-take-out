package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liudo
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 16:35:17
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * 按菜式ID选择计数
     *
     * @param ids ids
     * @return {@link Integer}
     */
    Integer selectCountByDishIds(List<String> ids);

    /**
     * 插入批
     *
     * @param setmealDishes setmeal菜
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 按setmeal id删除
     *
     * @param setmealIds setmealIds
     */
    void deleteBySetmealIds(List<Long> setmealIds);

    /**
     * 选择通过setmeal id列表
     * 选择通过setmeal id列表
     *
     * @param setmealId setmeal id
     * @return {@link List}<{@link SetmealDish}>
     */
    List<SetmealDish> selectListBySetmealId(Long setmealId);
}
