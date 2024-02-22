package com.sky.service;

import com.sky.vo.UserLoginVO;

public interface UserService {
    /*
    * 用户登录
    *
    * */
    UserLoginVO userLogin(String code);
}
