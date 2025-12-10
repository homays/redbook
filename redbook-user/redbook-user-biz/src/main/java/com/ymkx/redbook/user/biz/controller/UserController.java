package com.ymkx.redbook.user.biz.controller;

import com.ymkx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.user.biz.request.UpdatePasswordReq;
import com.ymkx.redbook.user.biz.service.UserService;
import com.ymkx.redbook.user.request.RegisterUserReqDTO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ymkx
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<String> register(@Validated @RequestBody RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }

    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Valid UpdatePasswordReq request) {
        return userService.updatePassword(request);
    }

}
