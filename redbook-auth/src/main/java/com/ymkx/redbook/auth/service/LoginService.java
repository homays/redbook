package com.ymkx.redbook.auth.service;

import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.request.LoginReq;

public interface LoginService {

    /**
     * 登录、注册
     * @param req 请求参数
     * @return 响应
     */
    Response<String> login(LoginReq req);

}
