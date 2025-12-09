package com.ymkx.redbook.user.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ymkx
 * @date 2025/12/9 16:51
 * @description
 */
@SpringBootApplication
@MapperScan("com.ymkx.domain.mapper")
public class RedBookUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedBookUserApplication.class, args);
        System.out.println("redbook-user ===> start success");
    }
}
