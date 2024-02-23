package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insert(List<DishFlavor> flavors);

    void deleteByDishId(List<Long> DishIds);

    @Select("select id, dish_id, name, value from dish_flavor where dish_id=#{dishId};")
    List<DishFlavor> getDishFlavor(Long dishId);

    @Select("select id, dish_id, name, value from dish_flavor where dish_id=#{id};")
    List<DishFlavor> getBydishId(Long id);
}
