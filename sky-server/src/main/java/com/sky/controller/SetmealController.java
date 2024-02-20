package com.sky.controller;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation("分页查询套餐")
    @GetMapping("/page")
    public Result<PageResult> selcectAllSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.selcectAllSetmeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
     * 添加套餐
     * */
    @ApiOperation("添加套餐")
    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    /*
     * 根据ID查询套餐
     * */
    @ApiOperation("根据ID查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> getSetmeal(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getSetmeal(id);
        return Result.success(setmealVO);
    }

    /*
     * 根据ID删除套餐
     * */
    @ApiOperation("根据ID删除套餐")
    @DeleteMapping()
    public Result<?> deleteByIds(@RequestParam List<Long> ids) {
        setmealService.deleteByids(ids);
        return Result.success();
    }

    /*
     * 停售起售套餐
     * */
    @ApiOperation("停售起售套餐")
    @PostMapping("/status/{status}")
    public Result<?> setStatus(Long id, @PathVariable Integer status) {
        setmealService.setStatus(id, status);
        return Result.success();
    }
    /*
    * 修改套餐
    * */
    @ApiOperation("修改套餐")
    @PutMapping()
    public Result<?> updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

}
