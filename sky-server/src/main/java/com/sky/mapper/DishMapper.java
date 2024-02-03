package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
    /*
     * 分页查询
     * */
    List<DishVO> pageQuery(DishVO dish);

    /*
     * 更新菜品
     * */
    void updateDish(Dish dish);

    Integer selcetCount(Dish dish);

    DishVO getDish(Long id);

    void deleteById(List<Long> ids);
}
