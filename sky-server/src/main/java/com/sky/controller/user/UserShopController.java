package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shop")
@Slf4j
@Api("店铺相关接口")
public class UserShopController {

    private static final String key = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询店铺状态
     * @return Result
     */
    @GetMapping("/status")
    @ApiOperation("查询店铺状态")
    public Result queryStatus(){

        Integer status = (Integer) redisTemplate.opsForValue().get(key);

        log.info("获取到的状态为：{}",status == 1 ? "营业中":"打烊中");

        return Result.success(status);

    }

}
