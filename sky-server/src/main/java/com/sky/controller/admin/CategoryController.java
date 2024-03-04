package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> queryPage(CategoryDTO categoryDTO){

         PageResult pageResult = categoryService.page(categoryDTO);

        return Result.success(pageResult);
    }

    /**
     * 添加分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    public Result add(@RequestBody CategoryDTO categoryDTO){

        categoryService.add(categoryDTO);

        return Result.success();
    }


}
