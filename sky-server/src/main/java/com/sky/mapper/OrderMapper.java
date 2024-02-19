package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    /*
     * 分页查询
     * */
    List<Orders> conditionSearchOrder(OrdersPageQueryDTO ordersPageQueryDTO);

    /*
     * 派送订单
     * */
    @Update("update orders set status = 4 where id=#{id};")
    void deliveryOrder(Long id);

    /*
     * 营业额
     * */
    Integer countByMap(Map map);

    /*
     * 根据动态条件
     * */
    Double sumByMap(Map map);
/*
* top10商品销量统计
* */
    List<GoodsSalesDTO> selectGoodsSales(LocalDateTime beginTime, LocalDateTime endTime);
}
