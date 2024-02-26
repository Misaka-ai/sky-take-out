package com.sky.controller.admin;

import com.sky.annotation.AutoUpdateFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "菜品管理")
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
    @ApiOperation(value = "分页查询菜品")
    @GetMapping("/page")
    public Result<PageResult> pageDish(DishPageQueryDTO dishPageQueryDTO) {

        PageResult pageResult = dishService.pageDish(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /*、
     * 修改菜品
     *
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
    public Result<?> deleteById(@RequestParam List<Long> ids) {
        dishService.deleteById(ids);
        return Result.success();
    }

    /*
     * 根据分类ID查询菜品
     * */
    @GetMapping("/list")
    public Result<List<DishVO>> selectByCategoryID(@RequestParam Integer categoryId) {
        List<DishVO> dishVOs = dishService.selectByCategoryID(categoryId);
        return Result.success(dishVOs);
    }

    /*
     * 修改菜品状态
     * */

    @PostMapping("/status/{status}")
    public Result<?> updateStatus(Long id, @PathVariable Integer status) {
        dishService.updateStatus(id, status);
        return Result.success();
    }

    /*
     * 添加菜品
     * */
    @PostMapping()
    public Result<?> insertDish(@RequestBody DishDTO dishDTO) {
        dishService.insertDish(dishDTO);

        return Result.success();

    }

}
