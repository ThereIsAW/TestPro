package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分页查询员工数据的参数模型")
public class EmployeePageQueryDTO implements Serializable {

    //员工姓名
    @ApiModelProperty
    private String name;

    //页码
    @ApiModelProperty
    private int page;

    //每页显示记录数
    @ApiModelProperty
    private int pageSize;

}
