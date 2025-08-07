package com.ymkx.redbook.auth;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedbookAuthApplicationTests {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testInsert() {
        redisTemplate.opsForValue().set("name", "ymkx");
    }

}
