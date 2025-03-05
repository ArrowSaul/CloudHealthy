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
public class Admin implements Serializable {
    // 序列化ID，用于对象序列化时的身份标识
    private static final long serialVersionUID = 1L;

    // 用户ID，唯一标识一个用户
    private Long id;

    // 用户姓名
    private String name;

    // 用户名，用于登录
    private String username;

    // 密码，用于登录验证
    private String password;

    // 性别，0未知 1男 2女
    private Integer sex;

    // 头像图片路径
    private String image;

    // 用户状态，1正常 0锁定
    private Integer status;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 创建用户的ID
    private Long createUser;

    // 更新用户的ID
    private Long updateUser;
}