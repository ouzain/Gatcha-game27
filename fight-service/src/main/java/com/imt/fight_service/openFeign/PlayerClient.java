package com.imt.fight_service.openFeign;

import com.imt.fight_service.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "player-service-client", url = "http://localhost:8082")
public interface PlayerClient {

    @GetMapping("/player/{id}")
    PlayerDto getPlayerById(@PathVariable("id") Long id);
}