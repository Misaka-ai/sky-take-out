package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingSerVice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@RequiredArgsConstructor
public class ShoppingController {
    private final ShoppingSerVice shoppingSerVice;

    /*
     * 添加到购物车
     *
     * */
    @PostMapping("/add")
    public Result<?> shopAdd(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingSerVice.addShopping(shoppingCartDTO);
        log.info("受到的信息为:" + shoppingCartDTO.toString());
        return Result.success();

    }

    /*
     * 通过用户ID查看购物车
     * */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> getShoppingsById() {
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCarts = shoppingSerVice.getShoppings(userId);
        return Result.success(shoppingCarts);

    }

    /*
     * 清空当前用户的购物车
     *
     * */
    @DeleteMapping("/clean")
    public Result<?> deletShoppingcart() {
        Long userId = BaseContext.getCurrentId();
        shoppingSerVice.deleteByid(userId);
        return Result.success();
    }

    /*
     * 减少购物车中的数量
     * */
    @PostMapping("/sub")
    public Result<?> deletOneByUserId(@RequestBody ShoppingCartDTO shoppingCartDTO ) {
        shoppingSerVice.deletOneByUserId(shoppingCartDTO);
        return Result.success();
    }

}
