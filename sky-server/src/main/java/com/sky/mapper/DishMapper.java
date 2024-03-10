package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    /**
     * 根据菜品分类查询总数
     * @param id
     * @return
     */
    @Select("select count(id) from dish where category_id = #{id}")
    Integer countByCategoryId(Long id);

    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    /**
     *
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> query(DishPageQueryDTO dishPageQueryDTO);
}
