package com.sky.mapper;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    /*
     * 取消订单
     * */
    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    /*
     *接单
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
    void rejectionOrder(Orders orders);

    List<Orders> conditionSearchOrder(OrdersPageQueryDTO ordersPageQueryDTO);
}
