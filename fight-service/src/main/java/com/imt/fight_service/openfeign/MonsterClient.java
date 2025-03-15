package com.imt.fight_service.openfeign;

import com.imt.fight_service.model.FightingMonster;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * Feign client pour appeler l’API Monster, enregistrée dans Eureka sous "monster-service"
 */
@FeignClient(name = "monster-service")
public interface MonsterClient {

    @GetMapping("/monsters/{id}")
    ResponseEntity<FightingMonster> getMonsterById(@PathVariable int id);

}
