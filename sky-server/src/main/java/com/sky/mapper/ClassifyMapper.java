package com.sky.mapper;

import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassifyMapper {

    /*
     * 判断分类名称是否重复
     * */
    @Select("select count(*) from category  where name=#{name};")
    int selectByname(String name);

    /*
     * 分页查询
     * */
    List<Category> selcetAllCategory(String name, Integer id, Integer status, Integer type);

    /*
     * 根据ID删除分类
     * */
    @Delete("delete from category where id=#{id};")
    void deleteById(Integer id);

    /*
     * 添加分类
     * */
    void insert(Category category);

    /*
     * 修改分类
     * */
    void updateCategory(Category category);

    /*
     * 修改状态
     * */
    void changeStatus(Integer id, Integer status);
}
