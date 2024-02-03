package com.sky.controller;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    /*
     * 分页查询菜品
     * */
    @GetMapping("/page")
    public Result<PageResult> pageDish(DishPageQueryDTO dishPageQueryDTO) {

        PageResult pageResult = dishService.pageDish(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /*、
     * 修改菜品
     * 、
     */
    @PutMapping
    public Result<?> updateDish(@RequestBody DishDTO dishDTO) {
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    /*
     * 根据ID查询菜品
     * */
    @GetMapping("/{id}")
    public Result<DishVO> getDish(@PathVariable Long id) {
        DishVO dishVO = dishService.getDish(id);
        return Result.success(dishVO);
    }

    /*
     * 根据ID删除菜品
     * */
    @DeleteMapping()
    public Result<?> deleteById(@Param("ids") String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        dishService.deleteById(idList);
        return Result.success();
    }
}
