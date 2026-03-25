package com.ymkx.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedBookAuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedBookAuthenticationApplication.class, args);
        System.out.println("redbook-authentication ===> start success");
    }
}
