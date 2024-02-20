package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "店铺窗台控制器")
public class ShopController {
    private final RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("设置状态")
    @PutMapping("/{status}")
    public Result<?> updateStatus(@PathVariable Integer status) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("ADMIN", status);
        return Result.success();
    }

    @ApiOperation("获取状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Integer o = (Integer) valueOperations.get("ADMIN");
        return Result.success(o == null ? 0 : o);

    }

}
