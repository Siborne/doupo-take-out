package com.doupo.service;

import com.doupo.dto.UserLoginDTO;
import com.doupo.entity.User;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
