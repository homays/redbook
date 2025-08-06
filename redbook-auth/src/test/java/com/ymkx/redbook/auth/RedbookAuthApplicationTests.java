package com.ymkx.redbook.auth;

import com.ymkx.domain.entity.UserDO;
import com.ymkx.domain.mapper.UserMapper;
import com.ymkx.framework.common.util.JsonUtils;
import jakarta.annotation.Resource;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class RedbookAuthApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testInsert() {
        UserDO userDO = UserDO.builder()
                .username("ymkx")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userMapper.insert(userDO);
    }

    @Test
    void testSelect() {
        UserDO userDO = userMapper.selectById(1953035569446219778L);
        System.out.println(JsonUtils.toJsonString(userDO));
    }

}
