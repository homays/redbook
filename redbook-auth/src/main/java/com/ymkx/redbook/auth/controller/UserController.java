package com.ymkx.redbook.auth.controller;

import com.ymkx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.request.UpdatePasswordReq;
import com.ymkx.redbook.auth.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
    private UserService userservice;

    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Valid UpdatePasswordReq request) {
        return Response.success(userservice.updatePassword(request));
    }

}
