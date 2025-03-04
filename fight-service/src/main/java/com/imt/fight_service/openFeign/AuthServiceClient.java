package com.imt.fight_service.OpenFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authentication-service", url = "http://localhost:8080/auth")
public interface AuthServiceClient {

    @GetMapping("/validate")
    boolean validateToken(@RequestHeader("Authorization") String token);
}