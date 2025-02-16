package com.imt.player_service.OpenFeing;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "Invocation-client")
public interface InvokClient {


    @PostMapping
    ResponseEntity<Integer> invokeMonster(@RequestHeader("Authorization") String token,
                                          @RequestParam String playerId);
}
