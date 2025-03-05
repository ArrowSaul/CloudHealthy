package com.customer.controller.user;

import com.customer.result.Result;
import com.customer.service.UserService;
import com.customer.constant.JwtClaimsConstant;
import com.customer.entity.User;
import com.customer.properties.JwtProperties;
import com.customer.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userController")
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "C端用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    //微信登录
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        log.info("返回:{}",userLoginVO);
        return Result.success(userLoginVO);
    }
    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Result<String> logout() {
        return Result.success();
    }
    //获取用户信息
    @GetMapping("/{id}")
    @ApiOperation("获取用户信息")
    public Result<User> info(@PathVariable long id) {
        log.info("获取用户信息:{}",id);
        User user = userService.info(id);
        return Result.success(user);
    }
    //更新用户信息
    @PutMapping("/update")
    @ApiOperation("更新用户信息")
    public Result<String> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("更新用户信息:{}",userUpdateDTO);
        userService.update(userUpdateDTO);
        return Result.success();
    }
}
