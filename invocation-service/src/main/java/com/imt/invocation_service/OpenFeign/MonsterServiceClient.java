package com.imt.invocation_service.OpenFeign;


import com.imt.invocation_service.InvoModel.ApiResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * Feign client pour appeler l’API Monster, enregistrée dans Eureka sous "monster-service"
 */
@FeignClient(name = "monster-service")
public interface MonsterServiceClient {


    @PostMapping("/summon")
    ResponseEntity<ApiResponse> generateRandomMonster();
}
