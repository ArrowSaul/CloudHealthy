package com.customer.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserUpdateDTO {
    // 用户昵称，确保不为空
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    // 用户手机号，必须符合中国大陆手机号格式
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    // 用户头像的URL地址
    private String avatar;
}