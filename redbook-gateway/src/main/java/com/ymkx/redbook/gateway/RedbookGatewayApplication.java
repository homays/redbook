package com.ymkx.redbook.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedbookGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedbookGatewayApplication.class, args);
        System.out.println("redbook-gateway ===> start success");
    }

}
