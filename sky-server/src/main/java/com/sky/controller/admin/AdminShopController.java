package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/shop")
@ApiOperation("店铺相关接口")
public class AdminShopController {
    private static final String key = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result changeStatus(@PathVariable Integer status){

        redisTemplate.opsForValue().set(key,status);
        log.info("当前店铺状态：{}",status == 1 ? "营业中":"打烊中");

        return Result.success();
    }

    /**
     * 查询店铺状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("查询店铺状态")
    public Result queryStatus(){

        Integer status = (Integer) redisTemplate.opsForValue().get(key);

        log.info("获取到的状态为：{}",status == 1 ? "营业中":"打烊中");

        return Result.success(status);

    }
}
