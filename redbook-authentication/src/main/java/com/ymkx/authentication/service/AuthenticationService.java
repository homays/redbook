package com.ymkx.authentication.service;

import com.ymkx.authentication.request.AuthenticationPrepareRequest;
import com.ymkx.authentication.response.AuthenticationPrepareResponse;
import com.ymkx.framework.common.response.Response;

/**
 * @author ymkx
 * @date 2026/3/24 16:06
 * @description TODO
 */
public interface AuthenticationService {

    /**
     * 认证前：获取认证参数
     */
    Response<AuthenticationPrepareResponse> authenticationPrepare(AuthenticationPrepareRequest request);

}
