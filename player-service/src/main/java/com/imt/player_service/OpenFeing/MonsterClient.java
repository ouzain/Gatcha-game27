package com.imt.player_service.OpenFeing;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "Monster-client")
public interface MonsterClient {

}
