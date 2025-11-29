package com.ymkx.redbook.auth.service;

public interface UserRegisterService {

    /**
     * 注册用户
     *
     * @param phone 手机号
     * @return 用户ID
     */
    String registerUser(String phone);

}
