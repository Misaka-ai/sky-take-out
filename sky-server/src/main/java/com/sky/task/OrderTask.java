package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
 * 订单任务
 * */
@Component
@Slf4j
@AllArgsConstructor
public class OrderTask {
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * ?")
    public void autoCancel() {
        LocalDateTime orderTime = LocalDateTime.now();
        orderMapper.updateByStatusBeforeTime(orderTime, Orders.PENDING_PAYMENT);

    }
    @Scheduled(cron = "0 0 2 * * ?")
    public void autocoplete(){
        orderMapper.update(Orders.DELIVERY_IN_PROGRESS);
    }
}
