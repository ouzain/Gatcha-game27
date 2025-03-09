package com.imt.player_service.OpenFeing;


import com.imt.player_service.Model.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "invocation-service")
public interface InvokClient {


    @PostMapping("/api/invocations/summon-monster")
    ResponseEntity<ApiResponse> invokeMonster(@RequestHeader("Authorization") String token);
}
