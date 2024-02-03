package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.exception.CheckException;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;

    public DishServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }

    @Override
    public PageResult pageDish(DishPageQueryDTO dishPageQueryDTO) {
        //开启分页
        Integer page = Objects.isNull(dishPageQueryDTO.getPage()) ? 1 : dishPageQueryDTO.getPage();
        Integer pageSize = Objects.isNull(dishPageQueryDTO.getPageSize()) ? 10 : dishPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        DishVO dish = new DishVO();
        BeanUtils.copyProperties(dishPageQueryDTO, dish);
        //原始查询
        List<DishVO> dishes = dishMapper.pageQuery(dish);
        PageResult pageResult = new PageResult();
        Page<DishVO> dishPage = (Page<DishVO>) dishes;
        pageResult.setTotal(dishPage.getTotal());
        pageResult.setRecords(dishPage.getResult());
        return pageResult;
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        //非我判断菜品名称
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        Integer count = dishMapper.selcetCount(dish);
        if (count > 0) {
            throw new CheckException("输入的菜品名称重复啦");
        }
        dish.setUpdateTime(LocalDateTime.now());
        dish.setUpdateUser(BaseContext.getCurrentId());
        dishMapper.updateDish(dish);


    }

    @Override
    public DishVO getDish(Long id) {
        return dishMapper.getDish(id);
    }

    @Override
    public void deleteById(List<Long> ids) {
dishMapper.deleteById(ids);
    }

}
