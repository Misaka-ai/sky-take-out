package com.sky.service.impl;


import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.*;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public WorkSpaceServiceImpl(DishMapper dishMapper, SetmealMapper setmealMapper, OrderMapper orderMapper, EmployeeMapper employeeMapper, UserMapper userMapper) {
        this.dishMapper = dishMapper;
        this.setmealMapper = setmealMapper;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;

    }

    @Override
    public DishOverViewVO overviewDishes() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.ENABLE);
        Integer discontinued = dishMapper.countByMap(map);
        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public SetmealOverViewVO overviewSetmeals() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        map.put("status", StatusConstant.ENABLE);
        Integer discontinued = setmealMapper.countByMap(map);
        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();

    }

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        //订单总数
        Integer totalOrders = orderMapper.countByMap(map);
        map.put("status", Orders.COMPLETED);
        //营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;
        //有效订单数
        Integer valiOrder = orderMapper.countByMap(map);
        Double unitPrice = 0.0;
        Double orderCompletionRate = 0.0;
        if (totalOrders != 0 && valiOrder != 0) {
            //订单完成率
            orderCompletionRate = valiOrder.doubleValue() / totalOrders;
            unitPrice = turnover / valiOrder;
        }
        //新增用户数
        Integer newUsers = userMapper.countByMap(map);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(valiOrder)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();

    }

    @Override
    public OrderOverViewVO overviewOrders() {
        Map map = new HashMap();
        //查寻全部订单
        Integer totalOrders = orderMapper.countByMap(map);
        //查询已完成订单
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);
        //以取消订单
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);
        //待派送订单
        map.put("status", Orders.DELIVERY_IN_PROGRESS);
        Integer deliveryInProgressOrders = orderMapper.countByMap(map);
        //待接单订单
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer toBeConfirmedOrders = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .allOrders(totalOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .deliveredOrders(deliveryInProgressOrders)
                .waitingOrders(toBeConfirmedOrders)
                .build();





    }
}
