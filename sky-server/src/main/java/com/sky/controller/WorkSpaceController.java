package com.sky.controller;

import com.sky.result.Result;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/admin/workspace")
@RestController
@Slf4j
@Api(tags = "查询菜品")
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;

    public WorkSpaceController(WorkSpaceService workSpaceService) {
        this.workSpaceService = workSpaceService;
    }

    /*
     * 查询菜品总览
     * */
    @ApiOperation(value = "查询菜品总总览")
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> overviewDishes() {
        DishOverViewVO dishOverViewVO = workSpaceService.overviewDishes();
        return Result.success(dishOverViewVO);
    }

    /*
     * 查询套餐总览
     * */
    @ApiOperation(value = "查询套餐总览")
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> setmealOverViewVOResult() {
        SetmealOverViewVO setmeal = workSpaceService.overviewSetmeals();
        return Result.success(setmeal);
    }

    /*
     * 查询今日数据
     * */
    @ApiOperation(value = "查询今日数据")
    @GetMapping("/businessData")
    public Result<BusinessDataVO> getBusinessData() {
        LocalDateTime begin = LocalDateTime.now().with(LocalDateTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalDateTime.MAX);

        BusinessDataVO businessDataVO = workSpaceService.getBusinessData(begin, end);
        return Result.success(businessDataVO);

    }
    /*
     *查询订单管理数据
     * */
    @ApiOperation(value = "订单管理数据")
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> overviewOrders() {
        OrderOverViewVO orderOverViewVO = workSpaceService.overviewOrders();
        return Result.success(orderOverViewVO);
    }

}
