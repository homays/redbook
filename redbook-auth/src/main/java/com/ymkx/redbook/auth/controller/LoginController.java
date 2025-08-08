package com.ymkx.redbook.auth.controller;

import com.ymkx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.request.LoginReq;
import com.ymkx.redbook.auth.service.LoginService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录")
    public Response<String> login(@Valid @RequestBody LoginReq req) {
        return loginService.login(req);
    }

}
