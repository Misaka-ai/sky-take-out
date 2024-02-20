package com.sky.mapper;

import com.sky.annotation.AutoInsertFill;
import com.sky.annotation.AutoUpdateFill;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /*
     * 分页查询套餐
     * */
    List<Setmeal> selcectAllSetmeal(Setmeal setmeal);

    /*
     * 判断套餐名字是否重复
     * */
    @Select("select count(*) from setmeal where name = #{name}")
    Integer selcetByname(String name);

    /*
     * 添加套餐
     * */
    @AutoInsertFill
    void insert(Setmeal setmeal);

    Integer countByMap(Map map);

    /*
     *根据ID查询套餐
     * */
    SetmealVO getSetmeal(Long id);

    /*
     * 查询菜品和套餐的关系
     * */
    List<SetmealDish> getSetmealDish(Long id);

    /*
     * 判断要删除的菜品是否为起售状态
     * */
    Integer selcectByIdsAndStatus(List<Long> ids, Integer status);

    /*
     * 根据ID删除套餐
     * */
    void deleteByids(List<Long> ids);

    /*
     * 根据套餐ID删除套餐和菜品的关联关系
     * */
    void deleteBySetmealId(List<Long> ids);

    /*
     * 停售启售套餐
     * */
    void setStatus(Long id, Integer status);

    /*
     * 更新套餐信息
     * */

    void updateSetmeal(SetmealDTO setmealDTO);


    void updateSetmealDish(SetmealDTO setmealDTO);
}
