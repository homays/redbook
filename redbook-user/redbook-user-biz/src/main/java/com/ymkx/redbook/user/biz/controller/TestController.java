package com.ymkx.redbook.user.biz.controller;

import com.ymkx.domain.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ymkx
 * @date 2026/1/7 16:38
 * @description 测试回收删除
 */
@RestController
public class TestController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/test")
    public void test(@RequestParam String userId) {
        userMapper.deleteById(userId);
    }

}
