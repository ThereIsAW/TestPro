package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
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

    /**
     * 分页查询
     * @param setmeal
     * @return
     */
    Page<SetmealVO> page(Setmeal setmeal);

    /**
     * 根据套餐id查询套餐内关联的菜品
     * @param setmealId
     * @return
     */
    List<SetmealDish> queryWithDish(Long setmealId);

    /**
     * 根据id修改套餐
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     *根据套餐id添加对应菜品
     * @param id
     * @param setmealDishes
     */
    void addwithDishId(Long id, List<SetmealDish> setmealDishes);

    /**
     * 根据id查询套餐及配套数据
     * @param id
     * @return
     */
    SetmealVO queryAll(Long id);

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 批量删除套餐
     * @param ids
     */
    void batchDeletion(List<Long> ids);

    /**
     * 批量删除配套菜品关系表
     * @param setmealIds
     */
    void deleteBySetmealIds(List<Long> setmealIds);
}
