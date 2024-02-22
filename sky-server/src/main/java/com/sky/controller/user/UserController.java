package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.UserService;
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
    public Result<List<Category>> getCategorys() {
        List<Category> categoryList = userService.getCategorys();
        return Result.success(categoryList);
    }
    /*
    * 根据分类
    * */
}
