package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 添加分类
     * @param categoryDTO
     */
    void add(CategoryDTO categoryDTO);

    /**
     * 修改分类信息
     * @param categoryDTO
     */
    void updateData(CategoryDTO categoryDTO);

    /**
     * 启用禁用相关分类
     * @param status
     * @param id
     */
    void changeStatus(Integer status,Long id);


    /**
     *根据id删除分类
     *@param id
     * @return
     */
    void deleteById(Long id);

}
