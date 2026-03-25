package com.ymkx.authentication.controller;

import com.ymkx.authentication.convert.AuthenticationConvertor;
import com.ymkx.authentication.request.AuthenticationPrepareRequest;
import com.ymkx.authentication.request.AuthenticationResultRequest;
import com.ymkx.authentication.request.AuthenticationStatusRequest;
import com.ymkx.authentication.response.AuthenticationPrepareResponse;
import com.ymkx.authentication.response.AuthenticationResultResponse;
import com.ymkx.authentication.response.AuthenticationStatusResponse;
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
     *
     */
    @PostMapping("/authentication/prepare")
    public Response<AuthenticationPrepareResponse> authenticationPrepare(@RequestBody AuthenticationPrepareRequest request) {
        AuthenticationConvertor.buildParam(request);
        return authenticationService.authenticationPrepare(request);
    }

    /**
     * 查询当前认证状态接口。 (用于给调用方查询认证状态)
     *
     * @param request 请求参数，通常包含认证标识等信息
     * @return Result<AuthenticationStatusResponse> 返回当前认证状态信息
     */
    @PostMapping("/authentication/status")
    public Response<AuthenticationStatusResponse> authenticationStatus(@RequestBody AuthenticationStatusRequest request) {
        AuthenticationConvertor.buildParam(request);
        return authenticationService.authenticationStatus(request);
    }

    /**
     * 获取认证结果接口。(获取认证具体的详情数据)
     *
     * @param request 请求参数，通常包含认证标识等信息
     * @return Result<AuthenticationResultResponse> 返回认证最终结果（成功/失败）
     */
    @PostMapping("/authentication/result")
    public Response<AuthenticationResultResponse> authenticationResult(@RequestBody AuthenticationResultRequest request) {
        AuthenticationConvertor.buildParam(request);
        return authenticationService.authenticationResult(request);
    }

}
