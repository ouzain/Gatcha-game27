package com.imt.fight_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.imt.fight_service.openFeign")
public class FightServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FightServiceApplication.class, args);
    }
}
