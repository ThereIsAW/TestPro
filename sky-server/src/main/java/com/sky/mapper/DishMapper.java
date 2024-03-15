package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
     *菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> query(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品相关数据
     * @param dishId
     * @return
     */
    @Select("select di.*,ca.name as categoryName from dish di left join category ca on di.category_id = ca.id where di.id= #{dishId}")
    DishVO queryWithCategory(Long dishId);

    /**
     * 使用任意字段条件查询
     * @param dish
     */
    List<Dish> queryAll(Dish dish);

    /**
     * 根据id删除菜品(批量)
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 批量依据id查询数据
     * @param DishIds
     */
    List<Dish> bulkQueriesById(List<Long> DishIds);


    /**
     * 更新菜品数据
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void updateById(Dish dish);
}
