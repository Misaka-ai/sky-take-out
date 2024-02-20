package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.CheckException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;

    private final DishFlavorMapper dishFlavorMapper;


    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
    }

    @Override
    public PageResult pageDish(DishPageQueryDTO dishPageQueryDTO) {
        //开启分页
        Integer page = Objects.isNull(dishPageQueryDTO.getPage()) ? 1 : dishPageQueryDTO.getPage();
        Integer pageSize = Objects.isNull(dishPageQueryDTO.getPageSize()) ? 10 : dishPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        DishVO dish = new DishVO();
        dish.setCategoryId(dishPageQueryDTO.getCategoryId());
        dish.setName(dishPageQueryDTO.getName());
        dish.setStatus(dishPageQueryDTO.getStatus());
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
        dishMapper.updateDish(dish);
        //更新口味
        dishFlavorMapper.deleteByDishId(Collections.singletonList(dish.getId()));
        List<DishFlavor> flavors = dishFlavorMapper.getDishFlavor(dish.getId());
        if (Objects.nonNull(flavors) && !flavors.isEmpty()) {
            flavors.forEach(item -> item.setDishId(dish.getId()));
            dishFlavorMapper.insert(flavors);

        }

    }

    @Override
    public DishVO getDish(Long id) {
        Dish dish = dishMapper.dish(id);

        DishVO dishVO = new DishVO();
        List<DishFlavor> dishFlavors = dishFlavorMapper.getDishFlavor(id);

        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    @Transactional
    public void deleteById(List<Long> ids) {
        Integer count = dishMapper.selcetCountByIdsAndStatus(ids, StatusConstant.ENABLE);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        dishMapper.deleteById(ids);
        dishFlavorMapper.deleteByDishId(ids);

    }

    @Override
    public List<DishVO> selectByCategoryID(Integer categoryId) {

        List<DishVO> dishVOS = dishMapper.selectByCategoryID(categoryId);
        return dishVOS;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);

        dishMapper.updateDish(dish);


    }

    @Override
    @Transactional
    public void insertDish(DishVO dishVO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVO, dish);
        Integer count = dishMapper.selcetCount(dish);
        if (count > 0) {
            throw new CheckException(MessageConstant.DISH_CHECK);
        }

        dishMapper.insertDish(dish);
        List<DishFlavor> flavors = dishVO.getFlavors();
        if (Objects.nonNull(flavors) && !flavors.isEmpty()) {
            flavors.forEach(item -> item.setDishId(dish.getId()));
            dishFlavorMapper.insert(flavors);

        }
    }

}
