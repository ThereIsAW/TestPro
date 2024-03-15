package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    /**
     * 根据id查询菜品相关数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品相关数据")
    public Result<DishVO> queryById(@PathVariable Long id){

        DishVO dishVO = dishService.queryById(id);

        return Result.success(dishVO);
    }

    /**
     * 根据id删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除菜品")
    public Result delete(@RequestParam List<Long> ids){
        //todo debug
        dishService.deleteById(ids);

        return Result.success();
    }

    /**
     * 更新菜品数据
     * @return
     */
    @PutMapping
    @ApiOperation("更新菜品数据")
    public Result updateDishwithFalvor(@RequestBody DishDTO dishDTO){

        dishService.updateWithFalvor(dishDTO);

        return Result.success();
    }

    /**
     * 起售、停售菜品
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("起售、停售菜品")
    public Result updateStatus(@PathVariable Integer status,Long id){

        dishService.updateStatus(status,id);

        return Result.success();

    }
}
