package com.customer.service;


import com.customer.dto.UserLoginDTO;
import com.customer.dto.UserRegisterDTO;
import com.customer.dto.CheckDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.entity.User;

public interface UserService {
    //登录
    User login(UserLoginDTO userLoginDTO);
    //根据用户id查询用户信息
    User info(Long id);
    //更新用户信息
    void update(UserUpdateDTO userUpdateDTO);
    //查询姓名是否存在
    String getById(Long id);
    //接收注册信息
    void update(UserRegisterDTO userRegisterDTO);
    //签到
    String checkIn(CheckDTO checkDTO);
    //签退
    String checkOut(CheckDTO checkDTO);
}
