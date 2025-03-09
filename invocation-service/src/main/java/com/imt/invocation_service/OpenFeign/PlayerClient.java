package com.imt.invocation_service.OpenFeign;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "player-service")
public interface PlayerClient {


}

