package com.customer.service;


import com.customer.entity.User;

public interface UserService {
    //登录
    User login(UserLoginDTO userLoginDTO);
    //根据用户id查询用户信息
    User info(Long id);
    //更新用户信息
    void update(UserUpdateDTO userUpdateDTO);
}
