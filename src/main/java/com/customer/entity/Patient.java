package com.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {
    /**
     * 序列化版本唯一标识符
     * 用于在序列化和反序列化过程中确保类的版本一致性和兼容性
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     * 用于唯一标识每个对象的主键
     */
    private Long id;

    /**
     * 用户ID
     * 关联用户表的外键，表示该对象属于哪个用户
     */
    private Long userId;

    /**
     * 名称
     * 对象的名称，如用户的姓名或地址的名称
     */
    private String name;

    /**
     * 电话号码
     * 对象的联系电话，用于联系和通信
     */
    private String phone;

    /**
     * 性别
     * 对象的性别，0表示未知，1表示男性，2表示女性
     */
    private Integer sex;

    /**
     * 身份证号码
     * 对象的身份证号，用于身份验证和信息加密
     */
    private String idNumber;

    /**
     * 是否默认
     * 标记对象是否为默认项，0表示非默认，1表示默认
     */
    private Integer isDefault;
}