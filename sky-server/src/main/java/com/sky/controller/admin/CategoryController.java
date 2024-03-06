package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理类接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> queryPage(CategoryPageQueryDTO categoryPageQueryDTO){

         PageResult pageResult = categoryService.page(categoryPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 添加分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("添加分类")
    public Result add(@RequestBody CategoryDTO categoryDTO){

        categoryService.add(categoryDTO);

        return Result.success();
    }

    /**
     * 修改分类信息
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("根据id修改分类信息")
    public Result updateData(@RequestBody CategoryDTO categoryDTO){

        categoryService.updateData(categoryDTO);

        return Result.success();
    }

    /**
     * 启用禁用相关分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("status/{status}")
    @ApiOperation("启用禁用相关分类")
    public Result changeStatus(@PathVariable Integer status,Long id){

        categoryService.changeStatus(status,id);

        return Result.success();

    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteById(Long id){

        categoryService.deleteById(id);

        return Result.success();
    }


}
