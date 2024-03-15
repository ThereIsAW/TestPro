package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealMapper {

    /**
     * 根据菜品id查询关联套餐
     * @param dishIds
     * @return
     */
    List<SetmealDish> queryByDishIds(List<Long> dishIds);

    /**
     * 查询是否存在指定分类的套餐
     * @param id
     * @return
     */
    Integer countByCategoryId(Long id);

    /**
     * 根据菜品id删除相关联的套餐
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);

}
