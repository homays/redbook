package com.ymkx.authentication.manager;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.ymkx.authentication.ability.AuthenticationAbility;
import com.ymkx.authentication.factory.AuthAbilityFactory;
import com.ymkx.authentication.param.AuthenticationParam;
import com.ymkx.authentication.request.AuthenticationPrepareRequest;
import com.ymkx.authentication.response.AuthenticationPrepareResponse;
import com.ymkx.authentication.result.AuthenticationResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ymkx
 * @date 2026/3/24 16:19
 * @description TODO
 */
@Slf4j
@Component
public class AuthManager {

    public static final String AUTH_REQUEST_ID = "authRequestId";
    public static final String AUTHENTICATION_PARAM = "authenticationParam";

    @Resource
    private AuthAbilityFactory authAbilityFactory;

    public AuthenticationPrepareResponse authenticationPrepare(AuthenticationPrepareRequest request) {
        log.info("authenticationPrepare param:{}", JSON.toJSONString(request));
        Assert.notNull(request.getAuthenticationParam(), AUTHENTICATION_PARAM);
        AuthenticationResult result = authAbilityFactory.execute(request.getAuthType(), AuthenticationAbility.class,
                authAbility -> {
                    AuthenticationParam param = AuthenticationParam.of(request.getAuthType(), request.getAuthRequestId(), request.getAuthenticationParam());
                    return authAbility.authenticationPrepare(param);
                });
        request.setAuthRequestId(result.getAuthRequestId());
        log.info("authenticationPrepare result:{}", JSON.toJSONString(result));
        return AuthenticationPrepareResponse.result(result.getAuthRequestId(), result.getAuthenticationResult());
    }

}
