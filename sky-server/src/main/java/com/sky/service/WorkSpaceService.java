package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface WorkSpaceService {

    /*
     *
     * 查询菜品总览
     * */
    DishOverViewVO overviewDishes();

    /*
     *
     * 查询套餐总览
     * */
    SetmealOverViewVO overviewSetmeals();

    /*
     *
     *今日数据查询
     * */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);
    /*
     *查询订单管理数据
     * */
    OrderOverViewVO overviewOrders();
}
