package com.customer.service;


import com.customer.dto.UserLoginDTO;
import com.customer.dto.UserPageQueryDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.entity.User;
import com.customer.result.PageResult;

public interface UserService {
    //登录
    User login(UserLoginDTO userLoginDTO);
    //修改用户信息
    void update(UserUpdateDTO userUpdateDTO);
    //根据用户id查询用户信息
    User getById(Long id);
    //分页查询用户信息
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);
}
