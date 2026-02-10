package com.doupo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doupo.constant.MessageConstant;
import com.doupo.dto.UserLoginDTO;
import com.doupo.entity.User;
import com.doupo.exception.LoginFailedException;
import com.doupo.mapper.UserMapper;
import com.doupo.properties.WeChatProperties;
import com.doupo.service.UserService;
import com.doupo.utils.HttpClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class IserServoceImpl implements UserService {

    //微信服务接口地址
    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private final WeChatProperties weChatProperties;
    private final UserMapper userMapper;

    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        //判断openid是否为空，如果为空则表示登录失败，抛出业务异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断当前用户是否为新用户
        User user = userMapper.getByOpenid(openid);

        //是新用户，自动完成注册
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        //返回这个用户对象
        return user;
    }

    /**
     * 获取微信用户openid
     *
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        //调用微信接口服务，获取当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }

}
