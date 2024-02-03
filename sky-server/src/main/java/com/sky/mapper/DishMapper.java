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

    /*
     * 判断是否重复
     * */
    Integer selcetCount(Dish dish);

    /*
     * 根据菜品ID查询菜品
     * */
    DishVO getDish(Long id);

    /*
     * 根据id删除
     * */
    void deleteById(List<Long> ids);

    /*
     * 根据分类ID查询
     * */
    List<DishVO> selectByCategoryID(Integer categoryId);

    /*
     *
     * 添加菜品
     * */
    void insertDish(Dish dish);
}
