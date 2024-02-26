package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户登录")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /*
     *
     * 用户登录
     * */
    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public Result<UserLoginVO> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("接收code:{}", userLoginDTO.getCode());
        UserLoginVO userLoginVO = userService.userLogin(userLoginDTO.getCode());
        return Result.success(userLoginVO);
    }

    /*
     *套餐菜品查询
     * */
    @ApiOperation("套餐菜品查询")
    @GetMapping("/category/list")
    public Result<List<Category>> getCategorys(Integer type) {
        List<Category> categoryList = userService.getCategorys(type);
        return Result.success(categoryList);
    }

    /*
     * 根据分类id查询菜品
     * */
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/dish/list")
    public Result<List<DishVO>> getDishs(Integer categoryId) {
        List<DishVO> dishVOList = userService.getDishs(categoryId);
        return Result.success(dishVOList);
    }

    /*
     *
     *根据分类id查询套餐
     * */
    @ApiOperation("根据分类id查询套餐")
    @GetMapping("/setmeal/list")
    public Result<List<Category>> getCategory(Integer categoryId) {

        List<Category> dishVOList = userService.getCategory(categoryId);
        return Result.success(dishVOList);
    }
    /*
    * 根据套餐id查询包含的菜品
    * */

}
