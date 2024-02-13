package com.sky.service.impl;


import com.sky.mapper.WorkSpaceMapper;
import com.sky.service.WorkSpaceService;
import com.sky.vo.DishOverViewVO;
import org.springframework.stereotype.Service;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    private final WorkSpaceMapper workSpaceMapper;

    public WorkSpaceServiceImpl(WorkSpaceMapper workSpaceMapper) {
        this.workSpaceMapper = workSpaceMapper;
    }


    @Override
    public DishOverViewVO overviewDishes() {
        DishOverViewVO dishOverViewVO = workSpaceMapper.overviewDishes();
        return dishOverViewVO;
    }
}
