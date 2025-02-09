package com.imt.fight_service.openFeign;

import com.imt.fight_service.dto.MonsterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "monster-service", url = "http://localhost:8083")
public interface MonsterClient {

    @GetMapping("/monsters/{id}")
    MonsterDto getMonsterById(@PathVariable("id") Long id);
}
