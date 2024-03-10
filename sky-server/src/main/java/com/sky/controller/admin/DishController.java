package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理模块")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 增加菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("增加菜品")
    public Result save(@RequestBody DishDTO dishDTO){

        dishService.save(dishDTO);

        return Result.success();

    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){

        PageResult pageResult = dishService.page(dishPageQueryDTO);

        return Result.success(pageResult);
    }
}
