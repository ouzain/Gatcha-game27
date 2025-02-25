package com.imt.monster_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MonsterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonsterServiceApplication.class, args);
	}

}
