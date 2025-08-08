package com.ymkx.redbook.auth.service;

import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.request.SendVerificationCodeReq;

public interface VerificationCodeService {

    /**
     * 发送短信验证码
     *
     * @param req 请求参数
     * @return 响应结果
     */
    Response<?> send(SendVerificationCodeReq req);

}
