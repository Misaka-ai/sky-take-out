package com.sky.mapper;

import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    void insert(Setmeal setmeal);
}
