package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 增加菜品
     * @param dishDTO
     */
    void save(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     *根据id查询菜品
     * @param id
     * @return
     */
    DishVO queryById(Long id);

    /**
     * 根据菜品id删除菜品
     * @param ids
     */
    void deleteById(List<Long> ids);

    /**
     * 更新菜品数据
     * @param dishDTO
     */
    void updateWithFalvor(DishDTO dishDTO);

    /**
     *起售、停售菜品
     * @param status
     */
    void updateStatus(Integer status,Long dishId);
}
