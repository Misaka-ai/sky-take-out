package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoInsertFill;
import com.sky.annotation.AutoUpdateFill;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SetmealMapper
 *
 * @author liliudong
 * @version 1.0
 * @description
 * @date 2023/7/31 18:56
 */
@Mapper
public interface SetmealMapper {
    /**
     * 选择列表
     *
     * @param setmeal setmeal
     * @return {@link List}<{@link Setmeal}>
     */
    List<Setmeal> selectList(Setmeal setmeal);
    /**
     * 插入
     *
     * @param setmeal setmeal
     */
    @AutoInsertFill
    void insert(Setmeal setmeal);

    /**
     * 选择计数由ids和地位
     *
     * @param ids    id
     * @param status 状态
     * @return {@link Integer}
     */
    Integer selectCountByIdsAndStatus(List<Long> ids, Integer status);

    /**
     * 删除由ids
     *
     * @param ids id
     */
    void deleteByIds(List<Long> ids);

    /**
     * 更新通过id
     *
     * @param setmeal setmeal
     */
    @AutoUpdateFill
    void updateById(Setmeal setmeal);

    /**
     * 选择页面
     *
     * @param setmeal setmeal
     * @return {@link Page}<{@link Setmeal}>
     */
    Page<Setmeal> selectPage(Setmeal setmeal);

    /**
     * 选择通过id
     *
     * @param id id
     * @return {@link Setmeal}
     */
    Setmeal selectById(Long id);
}
