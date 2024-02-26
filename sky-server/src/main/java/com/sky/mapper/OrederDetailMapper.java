package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrederDetailMapper {
    void insertBatch(List<OrderDetail> orderDetailList);

    @Select("select id, name, image, order_id, dish_id, setmeal_id, dish_flavor, number, amount from order_detail where order_id=#{oreders1Id};")
    List<OrderDetail> selcetByOrderId(Long orders1Id);
}
