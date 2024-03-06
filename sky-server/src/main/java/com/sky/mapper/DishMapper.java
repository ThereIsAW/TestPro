package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    @Select("select count(dish_id) from setmeal_dish where id = #{id}")
    Integer countByCategoryId(Long id);
}
