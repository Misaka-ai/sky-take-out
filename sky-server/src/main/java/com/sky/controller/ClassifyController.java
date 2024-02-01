package com.sky.controller;

import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.ClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/category")
@RestController
@Slf4j
@Api(tags = "分类管理")
public class ClassifyController {
    private final ClassifyService classifyService;

    public ClassifyController(ClassifyService classifyService) {
        this.classifyService = classifyService;
    }

    /*
     * 新增分类
     * */
    @ApiOperation(value = "新增分类")
    @PostMapping
    public Result<?> addCategory(@RequestBody Category category) {
        classifyService.addCategory(category);
        return Result.success();
    }

    /*
     *分页查询
     *
     * */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public Result<PageResult> selcetAllCategory(String name,
                                                Integer id,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                Integer status,
                                                Integer type) {
        PageResult pageResult = classifyService.selcetAllCategory(name, id, page, pageSize, status, type);
        return Result.success(pageResult);
    }

    /*
     *根据ID删除分页
     * */
    @ApiOperation(value = "根据ID删除分类")
    @DeleteMapping
    public Result<?> deleteById(@RequestParam Integer id) {
        classifyService.deleteById(id);
        return Result.success();
    }

    /*
     * 修改分类信息
     * */

    @ApiOperation(value = "修改分类信息")
    @PutMapping
    public Result<?> updateCategory(@RequestBody Category category) {
        classifyService.updateCategory(category);
        return Result.success();
    }

    /*
     *修改状态
     * */
    @ApiOperation(value = "修改状态")
    @PostMapping("/status/{status}")
    public Result<?> changeStatus(@RequestParam("id") Integer id,
                                  @PathVariable("status") Integer status) {
        classifyService.changeStatus(id, status);
        return Result.success();
    }
}