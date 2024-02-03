package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.exception.CheckException;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Override
    public void addSetmeal(SetmealDTO setmealDTO) {
        //判断套餐餐名称是否为空，长度是否符合标准
        String name = setmealDTO.getName();
        if (Objects.isNull(name) || name.length() < 2 || name.length() > 20) {
            throw new CheckException("输入的套餐名称不符合规范");
        }
        //判断套餐名称是否重复
        Integer count = setmealMapper.selcetByname(name);
        if (count > 0) {
            throw new CheckException("输入的套餐名称重复");
        }
        //判断价格是否为空并且是1-8位的数字并可有2位小数
        BigDecimal price = setmealDTO.getPrice();
        if (Objects.isNull(price) || price.toString().length() < 1 || price.toString().length() > 8) {
            throw new CheckException("输入的价格不符合规范");
        }
//        判断价格是否为数字,可能有2位小数
        if (!price.toString().matches("^[0-9]+(.[0-9]{2})?$")) {
            throw new CheckException("输入的价格不符合规范");
        }
        //判断描述是否长度是否大于200
        String description = setmealDTO.getDescription();
        if (Objects.isNull(description) || description.length() > 200) {
            throw new CheckException("输入的描述不符合规范");
        }
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        Long id = BaseContext.getCurrentId();
        setmeal.setCreateUser(id);
        setmeal.setUpdateUser(id);
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmealMapper.insert(setmeal);

    }
}
