package com.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    // 序列化ID，用于对象序列化时的身份标识
    private static final long serialVersionUID = 1L;
    // 用户ID，唯一标识一个用户
    private Long id;
    // 用户的OpenID，微信用户唯一标识
    private String openid;
    // 用户角色，0表示普通用户，1表示客服
    private Integer role;
    // 用户昵称
    private String nickname;
    // 用户头像URL
    private String avatar;
    // 用户手机号码
    private String phone;
    // 用户创建时间
    private LocalDateTime createTime;
    // 用户更新时间
    private LocalDateTime updateTime;
}