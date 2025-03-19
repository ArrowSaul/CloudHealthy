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

    // 管理员ID，唯一标识一个管理员
    private Long id;

    // 管理员姓名
    private String name;

    // 用户名，用于登录
    private String username;

    // 密码，用于登录验证
    private String password;

    // 性别，0未知 1男 2女
    private Integer sex;

    // 头像图片路径
    private String image;

    // 管理员状态，1正常 0锁定
    private Integer status;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 创建管理员的ID
    private Long createUser;

    // 更新管理员的ID
    private Long updateUser;
}