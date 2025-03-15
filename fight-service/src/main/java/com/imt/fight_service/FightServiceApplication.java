package com.imt.fight_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FightServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FightServiceApplication.class, args);
	}

}
