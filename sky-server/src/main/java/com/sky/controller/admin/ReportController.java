package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequestMapping("/admin/report")
@Api(tags = "数据统计接口")
@Slf4j
@RestController
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final OrderService orderService;


    /*
     *营业额统计
     * */
    @ApiOperation("营业额统计")
    @GetMapping("turnoverStatistics")
    /*public Result<TurnoverReportVO> ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        TurnoverReportVO turnoverReportVO = reportService.ordersStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }*/
    public Result<TurnoverReportVO> ordersuserStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        TurnoverReportVO turnoverReportVO = orderService.ordersStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    /*
     * 用户统计
     * */
    @ApiOperation("用户统计")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        UserReportVO userReportVO = reportService.userStatistics(begin, end);
        return Result.success(userReportVO);
    }

    /*
     *
     * 订单统计
     *
     *
     * */
    @ApiOperation("订单统计")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        OrderReportVO orderReportVO = reportService.orderStatistics(begin, end);
        return Result.success(orderReportVO);
    }

    /*
     * 查询销量排名top10
     * */
    @ApiOperation("查询销量排名top10")
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> orderTop10(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        SalesTop10ReportVO salesTop10ReportVO = reportService.orderTop10(begin, end);
        return Result.success(salesTop10ReportVO);
    }
}

