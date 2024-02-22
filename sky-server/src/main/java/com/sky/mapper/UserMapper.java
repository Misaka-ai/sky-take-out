package com.sky.mapper;

import com.sky.entity.Category;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    Integer countByMap(Map map);

    User selcetOne(String s);

    void insert(User user1);
}
