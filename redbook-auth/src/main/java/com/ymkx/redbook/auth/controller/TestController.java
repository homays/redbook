package com.ymkx.redbook.auth.controller;

import com.ymkx.domain.entity.UserDO;
import com.ymkx.domain.mapper.UserMapper;
import com.ymkx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.ymkx.framework.common.response.Response;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response<String> test() {
        return Response.success("Hello, 犬小哈专栏");
    }

    @GetMapping("/test2")
    @ApiOperationLog(description = "测试接口2")
    public Response<UserDO> test2() {
        UserDO userDO = userMapper.selectById(1953035569446219778L);
        return Response.success(userDO);
    }
}