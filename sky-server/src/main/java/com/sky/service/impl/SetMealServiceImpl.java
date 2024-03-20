package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealMapper setMealMapper;

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

        Setmeal setmeal = new Setmeal();

        BeanUtils.copyProperties(setmealPageQueryDTO,setmeal);

        Page<SetmealVO> page = setMealMapper.page(setmeal);

        //将菜品对应的口味查询出来并放进SetmealVO类中
        List<SetmealVO> setmealVOS = page.getResult();

        //todo 未debug测试stream流 能否将 setmealDishes 赋值到 SetmealVO 的属性中
        List<Long> setmealId = setmealVOS.stream()
                .filter(dish -> dish.getId() != null)
                .map(SetmealVO::getId)
                .collect(Collectors.toList());

        //遍历 依据id插入数据
        for(Long id : setmealId){
            List<SetmealDish> setmealDishes = setMealMapper.queryWithDish(id);
            setmealVOS.stream()
                    .filter(i -> i.getId().equals(id))
                    .forEach(i -> i.setSetmealDishes(setmealDishes));
        }

        return new PageResult(page.getTotal(),setmealVOS);
    }

    /**
     * 根据id修改套餐
     * @param setmealDTO
     */
    @Override
    @Transactional
    public SetmealVO update(SetmealDTO setmealDTO) {

        if(setmealDTO.getSetmealDishes() != null){

            //删除原有套餐
            setMealMapper.deleteByDishIds(Lists.newArrayList(setmealDTO.getId()));

            //添加新套餐
            setMealMapper.addwithDishId(setmealDTO.getId(),setmealDTO.getSetmealDishes());
            
        }

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        setMealMapper.update(setmeal);

        //todo 未debug检测是否能通过 association 标签获取 setmealDishes并赋值进 SetmealVO 的属性中
        SetmealVO setmealVO = setMealMapper.queryAll(setmealDTO.getId());

        return setmealVO;

    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO queryById(Long id) {

        SetmealVO setmealVO = setMealMapper.queryAll(id);

        return setmealVO;
    }

    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {

        //todo 未设置默认禁用
        Setmeal setmeal = Setmeal.builder().build();

        BeanUtils.copyProperties(setmealDTO,setmeal);

        //保存套餐及配套菜品关系
        setMealMapper.insert(setmeal);
        setMealMapper.addwithDishId(setmeal.getId(),setmealDTO.getSetmealDishes());

    }

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    @Override
    public void changeStatus(Integer status, Long id) {

        Setmeal setmeal = Setmeal.builder().status(status).id(id).build();

        setMealMapper.update(setmeal);

    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {

        //删除本表
        setMealMapper.batchDeletion(ids);

        //删除配套菜品关系表
        setMealMapper.deleteBySetmealIds(ids);
    }
}
