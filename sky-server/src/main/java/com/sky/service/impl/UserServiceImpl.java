package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.entity.Category;
import com.sky.entity.User;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JwtProperties jwtProperties;
    private final CategoryMapper categoryMapper;


    @Override
    public UserLoginVO userLogin(String code) {
        //验票
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", "wxd8b237bc2aee8cf4");
        params.put("secret", "54924d63fceccf34de005847ee6d8bdf");
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        String responseJson = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject jsonObject = JSON.parseObject(responseJson);

        //查询用户是否存在
        String openid = jsonObject.getString("openid");
        User user = userMapper.selcetOne(openid);
        if (Objects.isNull(user)) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        //返回token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(openid)
                .token(token)
                .build();
        return userLoginVO;
    }

    @Override
    public List<Category> getCategorys() {
        List<Category> categoryList = categoryMapper.getCategorys();
        return categoryList;
    }
}
