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
    private static final long serialVersionUID = 1L;
    private Long id;
    //微信用户唯一标识
    private String openid;
    //昵称
    private String nickName;
    //姓名
    private String name;
    //性别 0 女 1 男
    private String sex;
    //年龄
    private Double age;
    //手机号
    private String phone;
    //学号
    private String studentId;
    //身份证号
    private String idNumber;
    //学院
    private String college;
    //专业
    private String major;
    //头像
    private String avatar;
    //经验
    private Integer experience;
    //注册时间
    private LocalDateTime createTime;
}
