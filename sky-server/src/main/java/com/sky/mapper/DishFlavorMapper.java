package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 保存菜品口味
     * @param dishFlavorList
     */
    void saveFlavor(List<DishFlavor> dishFlavorList);

    /**
     * 查询菜品口味
     * @param dishId
     * @return
     */
    List<DishFlavor> queryBydDishId(Long dishId);

    /**
     * 根据菜品id删除配套口味
     * @param dishId
     */
     @Delete("delete from dish_flavor where id = #{dishId}")
    void deleteByDishId(Long dishId);
}
