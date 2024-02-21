package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/user")
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
    @PostMapping("/login")
    public Result<UserLoginVO> userLogin(UserLoginDTO userLoginDTO) {
        UserLoginVO userLoginVO = userService.userLogin(userLoginDTO);

        return Result.success(userLoginVO);
    }
}
