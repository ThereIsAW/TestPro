package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /**
     * 添加分类
     * @param category
     */
    @Insert("insert into category(type,name,sort,status,create_time,update_time,create_user,update_user) values " +
            "(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(OperationType.INSERT)
    void add(Category category);

    /**
     * 分类分页查询
     * @param category
     * @return
     */
    Page<Category> page(Category category);


    /**
     * 修改分类信息
     * @param category
     */
    @AutoFill(OperationType.UPDATE)
    void update(Category category);

}