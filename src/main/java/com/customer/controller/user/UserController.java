package com.customer.controller.user;

import com.customer.dto.UserLoginDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.result.Result;
import com.customer.service.UserService;
import com.customer.constant.JwtClaimsConstant;
import com.customer.entity.User;
import com.customer.properties.JwtProperties;
import com.customer.utils.JwtUtil;
import com.customer.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController("userUserController")
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "C端用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
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
        return Result.success(userLoginVO);
    }

    /**
     * 退出登录
     */
    @PutMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout(){
        log.info("退出登录");
        return Result.success();
    }

    /**
     * 修改用户信息
     * @param userUpdateDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改用户信息")
    public Result update(@RequestBody UserUpdateDTO userUpdateDTO){
        log.info("修改用户信息：{}",userUpdateDTO);
        userService.update(userUpdateDTO);
        return Result.success();
    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据用户id查询用户信息")
    public Result getById(@PathVariable Long id){
        log.info("查询用户信息,{}", id);
        User user = userService.getById(id);
        return Result.success(user);
    }
}
