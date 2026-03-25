package com.ymkx.authentication.ability;

import com.ymkx.authentication.context.AuthenticationContext;
import com.ymkx.authentication.emums.AuthStatusEnum;
import com.ymkx.authentication.emums.AuthenticationAbilityEnum;
import com.ymkx.authentication.param.AuthenticationParam;
import com.ymkx.authentication.request.TestAAuthenticationRequest;
import com.ymkx.authentication.response.AuthenticationResponse;
import com.ymkx.authentication.response.TestAAuthenticationResponse;
import com.ymkx.authentication.result.AuthenticationResult;
import com.ymkx.authentication.service.AuthenticationContextService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.ymkx.authentication.service.AuthenticationContextService.buildAuthenticationContext;

@Slf4j
@Component
public class TestAAbility implements AuthenticationAbility {

    @Resource
    private AuthenticationContextService authenticationContextService;

    @Override
    public AuthenticationResult authenticationPrepare(AuthenticationParam param) {
        AuthenticationContext<TestAAuthenticationRequest, TestAAuthenticationResponse> context = buildAuthenticationContext(param, TestAAuthenticationRequest.class);
        TestAAuthenticationRequest request = context.getAuthenticationParam();
        // 可以进行非空判断等操作
        authenticationContextService.store(context);
        return AuthenticationResult.result(context);
    }

    @Override
    public AuthenticationResult authenticationStatus(AuthenticationParam param) {
        AuthenticationContext<TestAAuthenticationRequest, AuthenticationResponse> context = authenticationContextService.restore(param, TestAAuthenticationRequest.class, AuthenticationResponse.class);
        if (Objects.isNull(context)) {
            return AuthenticationResult.result(AuthStatusEnum.INVALID);
        }
        return AuthenticationResult.result(context);
    }

    @Override
    public AuthenticationResult authenticationResult(AuthenticationParam param) {
        AuthenticationContext<TestAAuthenticationRequest, AuthenticationResponse> context = authenticationContextService.restore(param, TestAAuthenticationRequest.class, AuthenticationResponse.class);
        if (BooleanUtils.isTrue(context.getPass())) {
            return AuthenticationResult.result(context);
        }

        TestAAuthenticationRequest request = context.getAuthenticationParam();
        // TODO: 进行认证的操作，以及封装结果
        context.setAuthStatus(AuthStatusEnum.SUCCESS);
        authenticationContextService.store(context);
        authenticationContextService.remove(context.getAuthType(), context.getAuthRequestId());
        return AuthenticationResult.result(context);
    }


    @Override
    public String getName() {
        return AuthenticationAbilityEnum.TEST_A.getCode();
    }

}
