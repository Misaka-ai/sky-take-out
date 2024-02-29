package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.OrederDetailMapper;
import com.sky.mapper.ShopMapper;
import com.sky.result.PageResult;
import com.sky.server.WebSocketServer;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.vo.TurnoverReportVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    private OrederDetailMapper orederDetailMapper;
    private ShopMapper shopMapper;
    private AddressBookMapper addressBookMapper;
    private WebSocketServer webSocketServer;


    @Override
    public void cancelOrder(OrdersCancelDTO ordersCancelDTO) {
        Orders orders = new Orders();
        orders.setStatus(Orders.CANCELLED);
        orders.setId(ordersCancelDTO.getId());
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.cancelById(orders);
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
        Orders orders = new Orders();
        orders.setId(id);
        orderMapper.deliveryOrder(orders);
    }

    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {

        //查询购物车列表
        ShoppingCart shoppingCartQuery = new ShoppingCart();
        shoppingCartQuery.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCarts = shopMapper.selectByUserId(shoppingCartQuery);
        //判段查出的值是否为空
        if (shoppingCarts.isEmpty()) {
            throw new OrderBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        //查询地址信息
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (Objects.isNull(addressBook)) {
            throw new OrderBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Orders orders = new Orders();
        long number = System.currentTimeMillis();
        orders.setNumber(String.valueOf(number));
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(BaseContext.getCurrentId());
        orders.setAddressBookId(ordersSubmitDTO.getAddressBookId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayMethod(ordersSubmitDTO.getPayMethod());
        orders.setPayStatus(Orders.UN_PAID);
        //
        BigDecimal amount = new BigDecimal(0);
        for (ShoppingCart shoppingCart : shoppingCarts) {
            Integer number1 = shoppingCart.getNumber();
            BigDecimal amount1 = shoppingCart.getAmount();
            amount = amount.add(amount1.multiply(new BigDecimal(number1)));
        }
        orders.setAmount(amount);
        orders.setRemark(ordersSubmitDTO.getRemark());
        orders.setUserName(addressBook.getPhone());
        orders.setPhone(addressBook.getPhone());
        String adress = addressBook.getProvinceName() + addressBook.getCityName() + addressBook.getDistrictName() + addressBook.getDetail();
        orders.setAddress(adress);
        orders.setConsignee(addressBook.getConsignee());
        orders.setEstimatedDeliveryTime(ordersSubmitDTO.getEstimatedDeliveryTime());
        orders.setDeliveryStatus(ordersSubmitDTO.getDeliveryStatus());
        orders.setPackAmount(ordersSubmitDTO.getPackAmount());
        orders.setTablewareNumber(ordersSubmitDTO.getTablewareNumber());
        orders.setTablewareStatus(ordersSubmitDTO.getTablewareStatus());


        //订单
        orderMapper.submit(orders);
        //订单详情
        List<OrderDetail> orderDetailList = shoppingCarts.stream()
                .map(shoppingCart -> {
                    OrderDetail orderDetail = new OrderDetail();
                    BeanUtils.copyProperties(shoppingCart, orderDetail);
                    orderDetail.setOrderId(orders.getId());
                    return orderDetail;
                }).collect(Collectors.toList());
        orederDetailMapper.insertBatch(orderDetailList);
        //清空购物车
        shopMapper.deletByuserId(shoppingCartQuery);
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setId(orders.getId());
        orderSubmitVO.setOrderNumber(orders.getNumber());
        orderSubmitVO.setOrderAmount(orders.getAmount());
        orderSubmitVO.setOrderTime(orders.getOrderTime());
        return orderSubmitVO;
    }

    @Override
    public void payment(OrdersDTO ordersDTO) {
        paySuccess(ordersDTO.getNumber());
    }

    @Override
    public void paySuccess(String number) {
        //支付完成修改订单状态
        Orders ordersQuery = new Orders();
        ordersQuery.setNumber(number);
        Orders orders = orderMapper.selectOne(ordersQuery);
        //幂等=
        if (Objects.isNull(orders)) {
            return;
        }
        //如果当前状态不是待支付，则无需处理
        if (!Objects.equals(Orders.PENDING_PAYMENT, orders.getStatus())) {
            return;
        }
        orders.setStatus(Orders.TO_BE_CONFIRMED);
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setPayStatus(2);
        orderMapper.updateById(orders);
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("orderId", orders.getId());
        map.put("content", "订单来了");
        webSocketServer.sendToAllClient(JSON.toJSONString(map));


    }

    @Override
    public PageResult historyOrders(UserOrdersPageQueryDTO userOrdersPageQueryDTO) {

        //开启分页
        PageHelper.startPage(userOrdersPageQueryDTO.getPage(), userOrdersPageQueryDTO.getPageSize());

        //原始查询
        Orders orders = new Orders();
        orders.setStatus(userOrdersPageQueryDTO.getStatus());
        orders.setUserId(BaseContext.getCurrentId());
        //查询订单
        Page<Orders> ordersList = orderMapper.selectByStatus(orders);

        //查询订单详情
        List<OrderVO> orderVOArrayList = new ArrayList<>();
        for (Orders orders1 : ordersList) {
            Long orders1Id = orders1.getId();
            List<OrderDetail> orderDetailList = orederDetailMapper.selcetByOrderId(orders1Id);
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orders1, orderVO);
            orderVO.setOrderDetailList(orderDetailList);
            orderVOArrayList.add(orderVO);
        }
        return new PageResult(ordersList.getTotal(), orderVOArrayList);
    }

    @Override
    @Transactional
    public void repetition(Long id) {
        //根据订单ID查出之前的订单，在将查到的订单修改创建时间后再店家到数据库中
        Orders ordersQuery = new Orders();
        ordersQuery.setId(id);
        ordersQuery.setUserId(BaseContext.getCurrentId());
        //查出订单


        //查出订单详情
        List<OrderDetail> orderDetailList = orederDetailMapper.selcetByOrderId(ordersQuery.getId());

        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(orderDetail, shoppingCart);
            shoppingCart.setUserId(BaseContext.getCurrentId());
            shoppingCarts.add(shoppingCart);
        }

        //将订单详情添加到购物车
        shopMapper.insertBatch(shoppingCarts);


    }

    @Override
    public void cancel(Long id) {
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(Orders.CANCELLED);
        orders.setUserId(BaseContext.getCurrentId());
        orderMapper.cancelById(orders);
    }

    @Override
    public OrderVO orderDetail(Long id) {
        Orders orders = new Orders();
        orders.setId(id);
        Orders orders1 = orderMapper.selectById(orders);
        //查询订单详情
        List<OrderDetail> orderDetailList = orederDetailMapper.selcetByOrderId(orders.getId());

        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders1, orderVO);
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;

    }

    @Override
    public void reminder(Long orderId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("orderId", orderId);
        map.put("content", "催单来了");
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }

    @Override
    public TurnoverReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        //目标：根据时间区间查询出营业额
        ArrayList<LocalDate> localDates = new ArrayList<>();
        if (begin.isEqual(end)) {
            localDates.add(begin);
        } else {
            localDates.add(begin);
            LocalDate nextDate = begin.plusDays(1);
            localDates.add(nextDate);
            while (!nextDate.isEqual(end)) {
                nextDate = nextDate.plusDays(1);
                localDates.add(nextDate);
            }

        }
        List<Map<String, Object>> result = orderMapper.selectTurnoverStatistics(localDates);
        log.info(result.toString());

        String datelist = result.stream().map(item -> (String) item.get("time")).collect(Collectors.joining(","));
        String turnoverList = result.stream().map(item -> (BigDecimal) item.get("amount")).map(BigDecimal::toString).collect(Collectors.joining(","));
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        turnoverReportVO.setDateList(datelist);
        turnoverReportVO.setTurnoverList(turnoverList);

        return turnoverReportVO;
    }

}



