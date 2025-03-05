package com.customer.service.Impl;

import com.alibaba.fastjson.JSONObject;

import com.customer.constant.MessageConstant;
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
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public User login(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user;
        user = userMapper.getByOpenid(openid);
        if (user == null) {
            String avatar = userLoginDTO.getAvatar();
            String nickName = userLoginDTO.getNickName();
            user = User.builder()
                    .openid(openid)
                    .avatar(avatar)
                    .createTime(LocalDateTime.now())
                    .build();
            log.info("11111:{}",user);
            userMapper.insert(user);
        }
        user = userMapper.getByOpenid(openid);
        log.info("11222222:{}",user);
        return user;
    }

    public User info(Long id) {
        User user = userMapper.getInfo(id);
        return user;
    }

    public void update(UserUpdateDTO userUpdateDTO) {
        userMapper.update(userUpdateDTO);
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
}
