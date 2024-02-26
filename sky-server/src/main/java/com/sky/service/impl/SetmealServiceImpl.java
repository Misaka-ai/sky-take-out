package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * SetmealServiceImpl
 *
 * @author liliudong
 * @version 1.0
 * @description SetmealService
 * @date 2023/7/31 18:52
 */
@Service
@RequiredArgsConstructor
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;

    private final SetmealDishMapper setmealDishMapper;

    private final DishMapper dishMapper;

    @Override
    public List<Setmeal> listByCategoryId(Integer categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        return setmealMapper.selectList(setmeal);
    }

    @Override
    public List<SetmealDish> listDishById(Long setmealId) {
        List<SetmealDish> setmealDishList = setmealDishMapper.selectListBySetmealId(setmealId);
        return setmealDishList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        // 新增套餐，主键返回
        setmealMapper.insert(setmeal);
        // 新增套餐下的菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        insertBatchSetmealDish(setmeal.getId(), setmealDishes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        Integer count = setmealMapper.selectCountByIdsAndStatus(ids, StatusConstant.ENABLE);
        if (count > 0) {
            throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ON_SALE);
        }
        // 删除套餐
        setmealMapper.deleteByIds(ids);
        // 删除套餐关联的菜品
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 更新套餐
        setmealMapper.updateById(setmeal);
        // 清空套餐关联的菜品
        setmealDishMapper.deleteBySetmealIds(Collections.singletonList(setmealDTO.getId()));
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        insertBatchSetmealDish(setmeal.getId(), setmealDishes);
    }

    @Override
    public void updateStatusById(Long id, Integer status) {
        // 当套餐中存在菜品未起售，则不允许起售套餐
        if (Objects.equals(StatusConstant.ENABLE, status)) {
            Integer countDishBySetmealIdAndDisenble = dishMapper.selectCountByIdsAndStatus(Collections.singletonList(id), StatusConstant.DISABLE);
            if (countDishBySetmealIdAndDisenble > 0) {
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.updateById(setmeal);
    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealPageQueryDTO, setmeal);
        Integer categoryId = setmealPageQueryDTO.getCategoryId();
        setmeal.setCategoryId(categoryId);
        setmeal.setName(setmealPageQueryDTO.getName());
        Page<Setmeal> page = setmealMapper.selectPage(setmeal);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public SetmealVO queryById(Long id) {
        // 查询套餐
        Setmeal setmeal = setmealMapper.selectById(id);
        // 查询套餐下的菜品
        List<SetmealDish> setmealDishList = setmealDishMapper.selectListBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishList);
        return setmealVO;
    }

    private void insertBatchSetmealDish(Long setmealId, List<SetmealDish> setmealDishes) {
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishes.forEach(item -> item.setSetmealId(setmealId));
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }
}
