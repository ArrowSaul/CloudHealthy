package com.customer.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.customer.constant.MessageConstant;
import com.customer.constant.RoleConstant;
import com.customer.context.BaseContext;
import com.customer.dto.UserLoginDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.entity.User;
import com.customer.exception.LoginFailedException;
import com.customer.mapper.UserMapper;
import com.customer.properties.WeChatProperties;
import com.customer.service.UserService;
import com.customer.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    public User login(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .role(RoleConstant.USER)
                    .build();
            userMapper.insert(user);
        }
        return user;
    }
        public String getOpenid(String code) {
            try {
                // 微信小程序登录凭证校验接口URL
                String url = "https://api.weixin.qq.com/sns/jscode2session";
                // 准备参数
                Map<String, String> params = new HashMap<>();
                params.put("appid", weChatProperties.getAppid());
                params.put("secret", weChatProperties.getSecret());
                params.put("js_code", code);
                params.put("grant_type", "authorization_code");

                // 发送 GET 请求
                String response = HttpClientUtil.doGet(url, params);
                JSONObject jsonObject = JSONObject.parseObject(response);
                // 检查错误码
                if (jsonObject.containsKey("errcode")) {
                    // 处理错误情况
                    int errcode = jsonObject.getInteger("errcode");
                    String errmsg = jsonObject.getString("errmsg");
                    throw new RuntimeException("Error from WeChat: " + errmsg + " (errcode: " + errcode + ")");
                }
                // 获取 openid
                return jsonObject.getString("openid");
            } catch (Exception e) {
                // 记录日志或进行其他异常处理
                // logger.error("获取openid失败", e);
                throw new RuntimeException("获取openid失败", e);
            }
        }
    /**
     * 修改用户信息
     *
     * @param userUpdateDTO
     */
    public void update(UserUpdateDTO userUpdateDTO) {
        User user = User.builder()
                .id(BaseContext.getCurrentId())
                .nickname(userUpdateDTO.getNickname())
                .avatar(userUpdateDTO.getAvatar())
                .phone(userUpdateDTO.getPhone())
                .updateTime(LocalDateTime.now())
                .build();
        userMapper.update(user);
    }
}
