package com.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
@ApiModel(description = "用户注册数据模型")
public class UserRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("年龄")
    private String age;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("学号")
    private String studentId;
    @ApiModelProperty("身份证")
    private String idNumber;
    @ApiModelProperty("学院")
    private String college;
    @ApiModelProperty("专业")
    private String major;
}
