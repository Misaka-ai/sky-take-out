package com.sky.service;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;

public interface OrderService {
    /*
     * 取消订单
     * */
    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    /*
     *接收订单
     * */
    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    /*
     * 完成订单
     * */
    void completeOrder(Long id);

    /*
     * 各个状态的订单数量统计
     * */
    OrderStatisticsVO getOrderStatistics();

    /*
     * 拒单
     * */
    void rejectionOrder(OrdersRejectionDTO ordersRejectionDTO);

    /*
     * 订单搜索
     * */
    PageResult conditionSearchOrder(OrdersPageQueryDTO ordersPageQueryDTO);
}
