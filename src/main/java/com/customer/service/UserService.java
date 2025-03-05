package com.customer.service;


import com.customer.dto.UserLoginDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.entity.User;

public interface UserService {
    //登录
    User login(UserLoginDTO userLoginDTO);
    //修改用户信息
    void update(UserUpdateDTO userUpdateDTO);
}
