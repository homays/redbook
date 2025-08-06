package com.ymkx.redbook.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedbookAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedbookAuthApplication.class, args);
        System.out.println("redbook-auth ===> start success");
    }

}
