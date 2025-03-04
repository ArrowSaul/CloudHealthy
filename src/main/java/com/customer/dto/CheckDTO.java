package com.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel(description = "用户签到签退")
public class CheckDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty("用户id")
        private Long id;
        @ApiModelProperty("签到地点")
        private String location;
}
