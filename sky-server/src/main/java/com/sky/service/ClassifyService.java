package com.sky.service;

import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface ClassifyService {
    /*
     * 新增分类
     * */
    void addCategory(Category category);

    /*
     * 分页查询
     * */
    PageResult selcetAllCategory(String name, Integer id, Integer page, Integer pageSize, Integer status, Integer type);

    /*
     * 根据ID删除分类
     * */
    void deleteById(Integer id);

    /*
     * 修改分类信息
     * */
    void updateCategory(Category category);

    /*
     * 修改状态
     * */
    void changeStatus(Integer id, Integer status);

    /*
     * 根据类型查询分类
     * */
    List<Category> selectByType(Integer type);
}
