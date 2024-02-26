package com.sky.mapper;

import com.sky.annotation.AutoInsertFill;
import com.sky.annotation.AutoUpdateFill;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    /*
     * 分页查询
     * */
    List<DishVO> pageQuery(DishVO dish);

    /*
     * 更新菜品
     * */
    @AutoUpdateFill
    void updateDish(Dish dish);

    /*
     * 判断是否重复
     * */
    Integer selcetCount(Dish dish);

    /*
     * 根据菜品ID查询菜品
     * */
    @Select("select id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user from dish where id=#{id};")
    Dish dish(Long id);

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
    @AutoInsertFill
    void insertDish(Dish dish);

    Integer selectCountByIdsAndStatus(List<Long> ids, Integer status);

    /*
     * 查询菜品总览
     * */
    Integer countByMap(Map map);

    @Select("select * from dish where id=#{dishID};")
    Dish selectById(Long dishId);
}
