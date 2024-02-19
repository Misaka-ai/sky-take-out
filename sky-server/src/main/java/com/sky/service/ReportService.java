package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    /*
     * 订单数据统计
     * */
    TurnoverReportVO ordersStatistics(LocalDate begin, LocalDate end);

    /*
     * 用户统计
     * */
    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    /*
     * 订单统计
     * */
    OrderReportVO orderStatistics(LocalDate begin, LocalDate end);

    /*
     * top10商品销量统计
     * */
    SalesTop10ReportVO orderTop10(LocalDate begin, LocalDate end);
}
