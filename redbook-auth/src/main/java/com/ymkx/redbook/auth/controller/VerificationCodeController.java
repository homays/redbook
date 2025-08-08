package com.ymkx.redbook.auth.controller;

import com.ymkx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.request.SendVerificationCodeReq;
import com.ymkx.redbook.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VerificationCodeController {

    @Resource
    private VerificationCodeService verificationCodeService;

    @PostMapping("/verification/code/send")
    @ApiOperationLog(description = "发送短信验证码")
    public Response<?> send(@Validated @RequestBody SendVerificationCodeReq req) {
        return verificationCodeService.send(req);
    }

}