package com.customer.dto;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class PatientAddDTO {

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @NotNull(message = "性别不能为空")
    @Min(value = 0, message = "性别参数不合法")
    @Max(value = 2, message = "性别参数不合法")
    private Integer sex;

    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^\\d{17}[\\dXx]$", message = "身份证号格式不正确")
    private String idNumber;

    @NotNull(message = "默认状态不能为空")
    private Integer isDefault;
}