package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.CheckException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SetmealServiceImpl implements SetmealService {
    private final SetmealMapper setmealMapper;


    public SetmealServiceImpl(SetmealMapper setmealMapper, DishMapper dishMapper) {
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
        //添加套餐
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        Long id = BaseContext.getCurrentId();
        setmeal.setCreateUser(id);
        setmeal.setUpdateUser(id);
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmealMapper.insert(setmeal);
        //套餐包含的菜品


    }

    @Override
    public SetmealVO getSetmeal(Long id) {

        //查询套餐
        SetmealVO setmealVO = setmealMapper.getSetmeal(id);

        //查询套餐和菜品的关联关系
        List<SetmealDish> setmealDishes = setmealMapper.getSetmealDish(id);

        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    @Transactional
    @Override
    public void deleteByids(List<Long> ids) {
        //先判断套餐是否为起售状态
        Integer count = setmealMapper.selcectByIdsAndStatus(ids, StatusConstant.ENABLE);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        //删除套餐
        setmealMapper.deleteByids(ids);
        //删除套餐和菜品的关联关系
        setmealMapper.deleteBySetmealId(ids);


    }

    @Override
    public void setStatus(Long id, Integer status) {
        setmealMapper.setStatus(id, status);
    }

    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {


        //先更新套餐信息
        setmealMapper.updateSetmeal(setmealDTO);


    }
}
