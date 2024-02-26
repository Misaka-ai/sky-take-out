package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SetmealController
 *
 * @author liliudong
 * @version 1.0
 * @description 套餐控制器
 * @date 2023/7/31 18:50
 */
@RestController
@RequestMapping("/admin/setmeal")
@RequiredArgsConstructor
public class SetmealController {

    private final SetmealService setmealService;

    /**
     * 创建
     *
     * @param setmealDTO setmeal dto
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping
    public Result<?> create(@RequestBody SetmealDTO setmealDTO) {
        setmealService.create(setmealDTO);
        return Result.success();
    }

    /**
     * 删除由ids
     *
     * @param ids id
     * @return {@link Result}<{@link ?}>
     */
    @DeleteMapping
    public Result<?> deleteByIds(@RequestParam List<Long> ids) {
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 更新通过id
     *
     * @param setmealDTO setmeal dto
     * @return {@link Result}
     */
    @PutMapping
    public Result updateById(@RequestBody SetmealDTO setmealDTO) {
        setmealService.updateById(setmealDTO);
        return Result.success();
    }

    /**
     * 更新状态通过id
     * 更新状态
     *
     * @param status 状态
     * @param id     id
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping("/status/{status}")
    public Result<?> updateStatusById(Long id, @PathVariable Integer status) {
        setmealService.updateStatusById(id, status);
        return Result.success();
    }

    /**
     * 页面
     *
     * @param setmealPageQueryDTO setmeal页面查询dto
     * @return {@link Result}<{@link PageResult}>
     */
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询通过id
     *
     * @param id id
     * @return {@link Result}<{@link SetmealVO}>
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> queryById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.queryById(id);
        return Result.success(setmealVO);
    }
}
