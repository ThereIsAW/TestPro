package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setmealMapper;

    /**
     *分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {

        Category category = Category.builder().
                name(categoryPageQueryDTO.getName()).
                type(categoryPageQueryDTO.getType()).
                build();

        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.page(category);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 添加分类
     * @param categoryDTO
     */
    @Override
    public void add(CategoryDTO categoryDTO) {

        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO,category);

        //设置默认状态为禁用
        category.setStatus(StatusConstant.DISABLE);

        //设置建造/更新时间
        //category.setCreateTime(LocalDateTime.now());
        //category.setUpdateTime(LocalDateTime.now());
        //设置建造/更新用户
        //category.setCreateUser(BaseContext.getCurrentId());
        //category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.add(category);

    }

    /**
     * 修改分类信息
     * @param categoryDTO
     */
    @Override
    public void updateData(CategoryDTO categoryDTO) {

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        //获取更改用户的id 设置更新时间
        //category.setUpdateUser(BaseContext.getCurrentId());
        //category.setUpdateTime(LocalDateTime.now());

        categoryMapper.update(category);
    }


    /**
     * 启用禁用相关分类
     * @param status
     * @param id
     */
    @Override
    public void changeStatus(Integer status, Long id) {

        Category category = Category.builder().status(status).id(id).build();

        categoryMapper.update(category);
    }

     /**
     *根据id删除分类
     *@param id
     * @return
     */
    @Override
    public void deleteById(Long id) {

        //判断分类内是否有关联菜品或套餐，有则报错，不予删除
        Integer count = dishMapper.countByCategoryId(id);
        if(count > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }


        count = setmealMapper.countByCategoryId(id);
        if(count > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }


    }

    /**
     * 查询所有菜品分类
     * @return
     */
    @Override
    public List<Category> queryCategory(Integer type) {

        List<Category> categoryList = categoryMapper.queryCategory(type);

        return categoryList;
    }

}
