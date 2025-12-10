package com.ymkx.redbook.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ymkx
 */
@SpringBootApplication
@MapperScan("com.ymkx.domain.mapper")
@EnableFeignClients(basePackages = "com.ymkx.redbook.user")
public class RedbookAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedbookAuthApplication.class, args);
        System.out.println("redbook-auth ===> start success");
    }

}