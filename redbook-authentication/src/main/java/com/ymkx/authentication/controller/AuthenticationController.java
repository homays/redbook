package com.ymkx.authentication.controller;

import com.ymkx.authentication.convert.AuthenticationConvertor;
import com.ymkx.authentication.request.AuthenticationPrepareRequest;
import com.ymkx.authentication.response.AuthenticationPrepareResponse;
import com.ymkx.authentication.service.AuthenticationService;
import com.ymkx.framework.common.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ymkx
 * @date 2026/3/24 16:04
 * @description 认证控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * 用于认证前的准备工作
     *
     * @param request:actionType=认证方式 ,authenticationParam:认证参数
     */
    @PostMapping("/authentication/prepare")
    public Response<AuthenticationPrepareResponse> authenticationPrepare(@RequestBody AuthenticationPrepareRequest request) {
        AuthenticationConvertor.buildParam(request);
        return authenticationService.authenticationPrepare(request);
    }

}
