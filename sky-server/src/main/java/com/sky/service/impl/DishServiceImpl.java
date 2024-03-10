package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
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
}
