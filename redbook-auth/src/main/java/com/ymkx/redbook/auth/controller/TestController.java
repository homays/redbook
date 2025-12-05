package com.ymkx.redbook.auth.controller;

import com.ymkx.redbook.auth.request.SendVerificationCodeReq;
import com.ymkx.redbook.auth.service.TestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ymkx
 * @date 2025/12/4 11:16
 * @description TODO
 */
@RestController
@Slf4j
@RequestMapping("/retry")
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping("/test")
    public void test(@RequestBody SendVerificationCodeReq req) {
        testService.test(req);
    }

}
