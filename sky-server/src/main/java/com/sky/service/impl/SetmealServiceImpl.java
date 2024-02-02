package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Employee;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SetmealServiceImpl implements SetmealService {
    private final SetmealMapper setmealMapper;

    public SetmealServiceImpl(SetmealMapper setmealMapper) {
        this.setmealMapper = setmealMapper;
    }


    @Override
    public PageResult selcectAllSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = new PageResult();
        //判断传入的page与pageSize的值是否为空
        Integer page = Objects.isNull(setmealPageQueryDTO.getPage()) ? 1 : setmealPageQueryDTO.getPage();
        Integer pageSize = Objects.isNull(setmealPageQueryDTO.getPageSize()) ? 10 : setmealPageQueryDTO.getPageSize();

        //开启分页
        PageHelper.startPage(page, pageSize);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealPageQueryDTO, setmeal);

        //原始查询
        List<Setmeal> setmeals = setmealMapper.selcectAllSetmeal(setmeal);
        Page<Setmeal> setmealPage = (Page<Setmeal>) setmeals;

        pageResult.setRecords(setmealPage.getResult());
        pageResult.setTotal(setmealPage.getTotal());


        return pageResult;
    }
}
