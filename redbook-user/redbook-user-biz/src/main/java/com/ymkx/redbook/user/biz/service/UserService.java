package com.ymkx.redbook.user.biz.service;

import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.user.biz.request.UpdatePasswordReq;
import com.ymkx.redbook.user.request.RegisterUserReqDTO;

/**
 * @author ymkx
 */
public interface UserService {

    /**
     * 注册用户
     */
    Response<String> register(RegisterUserReqDTO registerUserReqDTO);


    /**
     * 修改密码
     */
    Response<?> updatePassword(UpdatePasswordReq req);
}
