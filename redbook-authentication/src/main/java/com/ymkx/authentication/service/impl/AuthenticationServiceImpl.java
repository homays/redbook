package com.ymkx.authentication.service.impl;

import cn.hutool.core.lang.Assert;
import com.ymkx.authentication.manager.AuthManager;
import com.ymkx.authentication.request.AuthenticationPrepareRequest;
import com.ymkx.authentication.response.AuthenticationPrepareResponse;
import com.ymkx.authentication.service.AuthenticationService;
import com.ymkx.framework.common.response.Response;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author ymkx
 * @date 2026/3/24 16:14
 * @description 认证service 实现类
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private AuthManager authManager;

    @Override
    public Response<AuthenticationPrepareResponse> authenticationPrepare(AuthenticationPrepareRequest request) {
        return execute(request.getAuthType(), authManager::authenticationPrepare, request);
    }

    /**
     * 统一参数校验
     */
    private <T, R> Response<R> execute(String authType, Function<T, R> businessFunction, T param) {
        Assert.notNull(param, "认证参数不能为空");
        Assert.notBlank(authType, "认证类型不能为空");
        R result = businessFunction.apply(param);
        return Response.success(result);
    }

}
