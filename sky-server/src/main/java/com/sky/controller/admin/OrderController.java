package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "订单管理")
@Slf4j
@RequestMapping("/admin/order")


public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*
     * 取消订单
     * */
    @ApiOperation(value = "取消订单")
    @PutMapping("/cancel")
    public Result<?> cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        orderService.cancelOrder(ordersCancelDTO);
        return Result.success();
    }

    /*
     * 接单
     * */
    @ApiOperation(value = "接单")
    @PutMapping("/confirm")
    public Result<?> confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }

    /*
     * 完成订单
     * */
    @ApiOperation(value = "完成订单")
    @PutMapping("complete/{id}")
    public Result<?> completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return Result.success();
    }


    /*
     * 各个状态的订单数量统计
     * */
    @ApiOperation(value = "各个状态的订单数量统计")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> getOrderStatistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.getOrderStatistics();

        return Result.success(orderStatisticsVO);
    }

    /*
     * 拒单
     * */
    @ApiOperation(value = "拒单")
    @PutMapping("/rejection")
    public Result<?> rejectionOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        orderService.rejectionOrder(ordersRejectionDTO);
        return Result.success();
    }

    /*
     * 订单搜索
     * */
    @ApiOperation(value = "订单搜索")
    @GetMapping("conditionSearch")
    public Result<PageResult> conditionSearchOrder(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = orderService.conditionSearchOrder(ordersPageQueryDTO);
        return Result.success(pageResult);
    }
    /*
    * 订单派送
    * */
    @ApiOperation(value = "订单派送")
    @PutMapping("delivery/{id}")
    public Result<?> deliveryOrder(@PathVariable Long id){
orderService.deliveryOrder(id);
        return Result.success();
    }
}
