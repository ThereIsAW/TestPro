package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SetMealMapper setMealMapper;

    /**
     * 增加菜品
     * @param dishDTO
     */
    @Override
    @Transactional
    public void save(DishDTO dishDTO) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO,dish);

        //保存新增的菜品
        dishMapper.save(dish);

        Long dishId = dish.getId();

        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();

        if(dishFlavorList != null && dishFlavorList.size() > 0){

            dishFlavorList.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });

            //保存新增的菜品对应口味
            dishFlavorMapper.saveFlavor(dishFlavorList);

        }

    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.query(dishPageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询菜品
     * @param dishId
     * @return
     */
    @Override
    public DishVO queryById(Long dishId) {

        //查询菜品相关数据
        DishVO dishVO = dishMapper.queryWithCategory(dishId);

        //根据菜品id查询口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.queryBydDishId(dishId);

        //将数据封装
        DishVO.builder().flavors(dishFlavors).build();

        return dishVO;
    }

    /**
     * 根据id删除菜品(批量)
     * @param ids
     */
    @Transactional
    @Override
    public void deleteById(List<Long> ids) {
        //查询是否起售，起售报删除异常
        List<Dish> dishList = dishMapper.bulkQueriesById(ids);

        //使用stream流提取返回的集合中菜品起售状态的数量
        long statusCount = dishList.stream()
                .filter(i -> i.getStatus().equals(StatusConstant.ENABLE))
                .count();
        if(statusCount != 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }

        //查询是否关联套餐，关联报删除异常
        List<SetmealDish> setmealDishList = setMealMapper.queryByDishIds(ids);
        if(setmealDishList.size() != 0 && setmealDishList != null){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除套餐菜品关联表中菜品关联的套餐
        setMealMapper.deleteByDishIds(ids);

        //删除菜品表中的菜品
        dishMapper.deleteByIds(ids);

    }

    /**
     * 更新菜品数据
     * @param dishDTO
     */
    @Transactional
    @Override
    public void updateWithFalvor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //根据id更新数据
        dishMapper.updateById(dish);

        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();

        //删除之前的关联口味数据
        dishFlavorMapper.deleteByDishId(dish.getId());

        if (dishFlavorList.size() != 0 && dishFlavorList != null) {

            //增加新传进来的口味数据
            dishFlavorMapper.saveFlavor(dishFlavorList);

        }

    }

    /**
     * 起售、停售菜品
     * @param status
     * @param dishId
     */
    @Override
    public void updateStatus(Integer status,Long dishId) {

        Dish dish = Dish.builder().id(dishId).status(status).build();
        dishMapper.updateById(dish);

    }

    /**
     * 根据分类id查询对应菜品
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> queryWithCategoryId(Long categoryId) {

        Dish dish = Dish.builder().categoryId(categoryId).build();

        List<Dish> dishList = dishMapper.queryAll(dish);

        return dishList;
    }


}
