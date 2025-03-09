package com.imt.invocation_service.OpenFeign;

import org.springframework.cloud.openfeign.FeignClient;
import com.imt.invocation_service.InvoModel.ApiResponse;
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
    @PostMapping("/api-auth/login")
    ResponseEntity<String> login(@RequestParam("username") String username,
                                 @RequestParam("password") String password);

    /**
     * Correspond à POST /api-auth/validate
     * On envoie le token dans l’en-tête "Authorization".
     * Retourne un username si OK, 401 sinon.
     */
    @GetMapping("/api-auth/validate")
    ResponseEntity<ApiResponse> validateToken(@RequestHeader("Authorization") String token);
}

