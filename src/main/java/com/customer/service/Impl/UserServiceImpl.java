package com.customer.service.Impl;

import com.alibaba.fastjson.JSONObject;

import com.customer.dto.UserRegisterDTO;
import com.customer.constant.MessageConstant;
import com.customer.dto.CheckDTO;
import com.customer.dto.UserLoginDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.entity.Check;
import com.customer.entity.User;
import com.customer.exception.LoginFailedException;
import com.customer.mapper.UserMapper;
import com.customer.properties.WeChatProperties;
import com.customer.service.UserService;
import com.customer.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                    .nickName(nickName)
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

    public String getById(Long id) {
        String name = userMapper.getById(id);
        return name;
    }

    public void update(UserRegisterDTO userRegisterDTO) {
        userMapper.updateById(userRegisterDTO);
    }

    @Override
    public String checkIn(CheckDTO checkDTO) {
        //判断今日是否签过到
            //查询最新一条
        Check checkTodayNew=userMapper.selectTodyCheck(checkDTO.getId());
        //没有
            //创建
            Check check;
        if(checkTodayNew==null)
        {
            check = Check.builder()
                    .checkInLocation(checkDTO.getLocation())
                    .userId(checkDTO.getId())
                    .checkInTime(LocalDateTime.now())
                    .build();
            userMapper.chenkInInsert(check);
            return "签到成功";
        }else{
            //添加一条签退为空的记录
            //没有
            //提醒先签退
            if(checkTodayNew.getCheckOutTime()==null)
            {
                return "需要先进行签退,才能进行签到";
            }else {
                //签到过，查询最新一条记录是否签退出过
                //签退了
                Integer num = userMapper.chenkTodyCount(checkDTO.getId());
                log.info("今天第{}",num);

                check = Check.builder()
                        .checkInLocation(checkDTO.getLocation())
                        .userId(checkDTO.getId())
                        .checkInTime(LocalDateTime.now())
                        .roundNumber(num + 1)
                        .build();
                userMapper.chenkInInsertNoNew(check);
                return "签到成功";
            }
        }
    }
    @Autowired
    private SqlSession sqlSession;
    @Override
    public String checkOut(CheckDTO checkDTO) {
        //判断今日是否签过到
            //没有，提醒签到再签退
        Check checkTodayNew=userMapper.selectTodyCheck(checkDTO.getId());
        if(checkTodayNew==null){
            return "请先签到，才可签退哦";
        }else{
            //签过，判断最新一条是否签退过
            if(checkTodayNew.getCheckOutTime()==null){
                //否，签退
                Check check = Check.builder()
                        .checkOutLocation(checkDTO.getLocation())
                        .userId(checkDTO.getId())
                        .checkOutTime(LocalDateTime.now())
                        .build();

                //提升经验
                    //查看时长


                    userMapper.chenkUpdateNew(check,checkTodayNew.getId());
                    Double a= userMapper.selectTimeD(checkDTO.getId());
                    log.info("时长{}",a);

                    Double jingyan = (a / 30) * 1.5; // 计算经验值

                    BigDecimal roundedJingyan = BigDecimal.valueOf(jingyan).setScale(4, RoundingMode.HALF_UP);
                    log.info("添加经验{}",roundedJingyan.doubleValue());
                    userMapper.insertJingyan(roundedJingyan.doubleValue(),checkDTO.getId());
                    //添加经验
                return "签退成功,添加"+roundedJingyan.doubleValue()+"经验";
            }else{
                //是，签退，提醒签到
                return "请先签到，才可签退哦";
            }

        }

    }

    //    public String getOpenid(String code) {
//        Map<String, String> map = new HashMap<>();
//        map.put("appid", weChatProperties.getAppid());
//        map.put("secret", weChatProperties.getSecret());
//        map.put("js_code", code);
//        map.put("grant_type", "authorization_code");
//        String json = HttpClientUtil.doGet(WX_LOGIN, map);
//        JSONObject jsonObject = JSON.parseObject(json);
//        String openid = jsonObject.getString("openid");
//        return openid;
//    }
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
