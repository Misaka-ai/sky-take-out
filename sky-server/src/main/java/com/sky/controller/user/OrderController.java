package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.dto.UserOrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("UserController")
@RequestMapping("/user/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/submit")
    public Result<?> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @PutMapping("/payment")
    public Result<?> payment(@RequestBody OrdersPageQueryDTO ordersPageQueryDTO) {
        orderService.payment(ordersPageQueryDTO);
        return Result.success();
    }

    @PutMapping("/paySuccess/{number}")
    public Result<?> paySuccess(@PathVariable String number) {
        orderService.paySuccess(number);
        return Result.success();
    }

    /*
     * 用户历史订单查询
     * */
    @GetMapping("/historyOrders")
    public Result<PageResult> historyOrders(UserOrdersPageQueryDTO userOrdersPageQueryDTO) {
        PageResult pageResult = orderService.historyOrders(userOrdersPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
     *
     * 再来一单
     * */
    @PostMapping("/repetition/{id}")
    public Result<?> repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }

    /*
     *取消订单
     * */
    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return Result.success();
    }

    /*
     * 查询订单详情
     *
     * */
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> orderDetail(@PathVariable Long id) {
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);

    }

}
