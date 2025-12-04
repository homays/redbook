package com.ymkx.redbook.auth.service;

import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.request.UpdatePasswordReq;

/**
 * @author ymkx
 */
public interface UserService {

    /**
     * 注册用户
     *
     * @param phone 手机号
     * @return 用户ID
     */
    String registerUser(String phone);


    /**
     * 修改密码
     */
    Response<?> updatePassword(UpdatePasswordReq req);
}
