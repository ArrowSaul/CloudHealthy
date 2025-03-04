package com.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
@ApiModel(description = "用户登录数据模型")
public class UserLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("微信授权码")
    private String code;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("昵称")
    private String nickName;
}
