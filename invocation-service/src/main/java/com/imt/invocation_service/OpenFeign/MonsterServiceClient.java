package com.imt.invocation_service.OpenFeign;


import com.imt.invocation_service.InvoModel.BaseMonster;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


/**
 * Feign client pour appeler l’API Monster, enregistrée dans Eureka sous "monster-service"
 */
@FeignClient(name = "monster-service")
public interface MonsterServiceClient {

    /**
     * Correspond à POST /monsters/add
     * Suppose que l’API Monstre renvoie un objet Monster en JSON
     */
    @PostMapping(value = "/monsters/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    Integer addMonster(@RequestBody BaseMonster baseMonster);

}
