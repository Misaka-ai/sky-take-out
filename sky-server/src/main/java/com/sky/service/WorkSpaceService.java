package com.sky.service;

import com.sky.vo.DishOverViewVO;
import org.springframework.stereotype.Service;

@Service
public interface WorkSpaceService {

    /*
    *
    * 查询菜品总览
    * */
    DishOverViewVO overviewDishes();
}
