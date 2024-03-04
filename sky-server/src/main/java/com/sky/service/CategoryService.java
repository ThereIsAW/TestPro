package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.result.PageResult;

public interface CategoryService {

    /**
     * 分类分页查询
     * @param categoryDTO
     * @return
     */
    PageResult page(CategoryDTO categoryDTO);

    /**
     * 添加分类
     * @param categoryDTO
     */
    void add(CategoryDTO categoryDTO);
}
