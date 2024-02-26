package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

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

    /*
     * 订单派送
     * */
    void deliveryOrder(Long id);

    /*
     * 用户下订单
     * */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    void payment(OrdersPageQueryDTO ordersPageQueryDTO);

    void paySuccess(String number);

    /*
     *历史订单查询
     * */
    PageResult historyOrders(UserOrdersPageQueryDTO userOrdersPageQueryDTO);

    /*
     *
     * 再来一单
     * */
    void repetition(Long id);

    /*
     *取消订单
     * */
    void cancel(Long id);

    /*
     * 查询订单详情
     *
     * */
    OrderVO orderDetail(Long id);
}
