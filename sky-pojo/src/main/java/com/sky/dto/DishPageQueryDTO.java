package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishPageQueryDTO implements Serializable {

    private Integer page;

    private Integer pageSize;

    private String name;

    //分类id
    private Long categoryId;

    //状态 0表示禁用 1表示启用
    private Integer status;

}
