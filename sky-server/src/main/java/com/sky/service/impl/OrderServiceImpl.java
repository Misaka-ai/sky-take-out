package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public void cancelOrder(OrdersCancelDTO ordersCancelDTO) {
        orderMapper.cancelOrder(ordersCancelDTO);
    }

    @Override
    public void confirmOrder(OrdersConfirmDTO ordersConfirmDTO) {
        orderMapper.confirmOrder(ordersConfirmDTO);
    }

    @Override
    public void completeOrder(Long id) {
        orderMapper.completeOrder(id);
    }

    @Override
    public OrderStatisticsVO getOrderStatistics() {

        return orderMapper.getOrderStatistics();

    }

    @Override
    public void rejectionOrder(OrdersRejectionDTO ordersRejectionDTO) {
        Orders orders = new Orders();
        orders.setId(ordersRejectionDTO.getId());
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orderMapper.rejectionOrder(orders);
    }

    @Override
    public PageResult conditionSearchOrder(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = new PageResult();


        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        //原始查询
        List<Orders> ordersList = orderMapper.conditionSearchOrder(ordersPageQueryDTO);
        Page<Orders> ordersPage = (Page<Orders>) ordersList;
        pageResult.setRecords(ordersPage.getResult());
        pageResult.setTotal(ordersPage.getTotal());


        return pageResult;
    }

    @Override
    public void deliveryOrder(Long id) {
        orderMapper.deliveryOrder(id);
    }
}
