package com.sky.controller;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "套餐管理")
@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetmealController {
    private final SetmealService setmealService;

    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    /*
     * 分页查询套餐
     * */
    @GetMapping("/page")
    public Result<PageResult> selcectAllSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.selcectAllSetmeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
}
