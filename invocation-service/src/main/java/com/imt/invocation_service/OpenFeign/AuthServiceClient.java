package com.imt.invocation_service.OpenFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {
    @GetMapping("/api-auth/validate")
    ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);
}
