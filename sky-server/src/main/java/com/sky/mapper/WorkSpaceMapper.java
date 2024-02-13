package com.sky.mapper;

import com.sky.vo.DishOverViewVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkSpaceMapper {
    DishOverViewVO overviewDishes();
}
