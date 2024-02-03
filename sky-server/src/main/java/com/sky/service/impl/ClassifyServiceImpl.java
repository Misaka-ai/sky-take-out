package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.entity.Category;
import com.sky.mapper.ClassifyMapper;
import com.sky.result.PageResult;
import com.sky.service.ClassifyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassifyServiceImpl implements ClassifyService {
    private final ClassifyMapper classifyMapper;

    public ClassifyServiceImpl(ClassifyMapper classifyMapper) {
        this.classifyMapper = classifyMapper;
    }

    @Override
    public void addCategory(Category category) {
        //判断分类的名称是否重复
        int count = classifyMapper.selectByname(category.getName());
        if (count > 0) {
            throw new RuntimeException("分类名称重复");
        }
        category.setCreateUser(BaseContext.getCurrentId());
        category.setStatus(0);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        classifyMapper.insert(category);
    }

    @Override
    public PageResult selcetAllCategory(String name, Integer id, Integer page, Integer pageSize, Integer status, Integer type) {
        PageResult pageResult = new PageResult();
        //开启分页
        PageHelper.startPage(page, pageSize);
        //原始查询
        List<Category> categories = classifyMapper.selcetAllCategory(name, id, status, type);
        Page<Category> page1 = (Page<Category>) categories;
        pageResult.setTotal(page1.getTotal());
        pageResult.setRecords(page1.getResult());

        return pageResult;
    }

    @Override
    public void deleteById(Integer id) {
        classifyMapper.deleteById(id);
    }

    @Override
    public void updateCategory(Category category) {
        classifyMapper.updateCategory(category);
    }

    @Override
    public void changeStatus(Integer id, Integer status) {

        classifyMapper.changeStatus(id, status);

    }

    @Override
    public List<Category> selectByType(Integer type) {
        List<Category> categories = classifyMapper.selectByType(type);

        return categories;
    }
}
