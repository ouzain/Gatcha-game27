package com.imt.invocation_service.OpenFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client pour appeler l’API Auth, enregistrée dans Eureka sous "auth-service"
 */
@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    /**
     * Correspond à GET /api-auth/login
     * On récupère un token si username/password valides.
     */
    @GetMapping("/api-auth/login")
    ResponseEntity<String> login(@RequestParam("username") String username,
                                 @RequestParam("password") String password);

    /**
     * Correspond à POST /api-auth/validate
     * On envoie le token dans l’en-tête "Authorization".
     * Retourne un username si OK, 401 sinon.
     */
    @PostMapping("/api-auth/validate")
    ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);
}
