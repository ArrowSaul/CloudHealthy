package com.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户分页查询时返回的数据模型")
public class UserPageQueryDTO {
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("页码")
    private int page;
    @ApiModelProperty("每页显示记录数")
    private int pageSize;
}
