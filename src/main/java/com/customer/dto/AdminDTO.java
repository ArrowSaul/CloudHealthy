package com.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "新增管理员时传递的数据模型")
public class AdminDTO implements Serializable {
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("头像")
    private String image;
    @ApiModelProperty("性别")
    private String sex;
}
