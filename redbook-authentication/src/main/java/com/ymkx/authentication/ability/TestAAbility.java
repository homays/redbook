package com.ymkx.authentication.ability;

import com.ymkx.authentication.context.AuthenticationContext;
import com.ymkx.authentication.emums.AuthenticationAbilityEnum;
import com.ymkx.authentication.param.AuthenticationParam;
import com.ymkx.authentication.request.TestAAuthenticationRequest;
import com.ymkx.authentication.response.TestAAuthenticationResponse;
import com.ymkx.authentication.result.AuthenticationResult;
import com.ymkx.authentication.service.AuthenticationContextService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    public String getName() {
        return AuthenticationAbilityEnum.TEST_A.getCode();
    }

}
