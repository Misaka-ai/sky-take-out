package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.entity.Category;
import com.sky.entity.DishFlavor;
import com.sky.entity.User;
import com.sky.mapper.*;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String URL = "https://api.weixin.qq.com/sns/jscode2session";
    private final UserMapper userMapper;
    private final JwtProperties jwtProperties;
    private final CategoryMapper categoryMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    private final DishFlavorMapper dishFlavorMapper;
    private final WeChatProperties weChatProperties;


    @Override
    public UserLoginVO userLogin(String code) {
        //验票
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", weChatProperties.getAppid());
        params.put("secret", weChatProperties.getSecret());
        params.put("js_code", code);
        params.put("grant_type", AUTHORIZATION_CODE);
        String responseJson = HttpClientUtil.doGet(URL, params);
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
    public List<Category> getCategorys(Integer type) {
        return categoryMapper.getCategorys(type);
    }

    @Override
    public List<DishVO> getDishs(Integer categoryId) {
        //根据分类查询菜品
        List<DishVO> dishVOList = dishMapper.selectByCategoryID(categoryId);
        //通过获得菜品Id来查询菜品口味
        for (DishVO dishVO : dishVOList) {
            List<DishFlavor> dishFlavors = dishFlavorMapper.getBydishId(dishVO.getId());
            dishVO.setFlavors(dishFlavors);
        }

        return dishVOList;
    }

    /*
     *
     *根据分类id查询套餐
     * */
    @Override
    public List<Category> getCategory(Integer categoryId) {
        return categoryMapper.getCategory(categoryId);
    }

    @Override
    public List<DishItemVO> getDishItem(Integer id) {

        return setmealMapper.getDishItem(id);
    }
}
