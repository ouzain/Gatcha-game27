package com.imt.invocation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // pour l'enregistrement sur Eureka
@EnableFeignClients //pour la détection des clients Feign
public class InvocationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvocationServiceApplication.class, args);
	}

}
