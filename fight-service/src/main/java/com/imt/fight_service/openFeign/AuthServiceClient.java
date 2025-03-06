package com.imt.fight_service.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Client Feign pour communiquer avec le service d'authentification.
 * 
 * Utilisé pour valider les tokens JWT des joueurs.
 */
@FeignClient(name = "authentication-service", url = "http://localhost:8080/auth")
public interface AuthServiceClient {

    /**
     * Vérifie si un token JWT est valide.
     * 
     * @param token Token JWT du joueur.
     * @return true si le token est valide, false sinon.
     */
    @GetMapping("/validate")
    boolean validateToken(@RequestHeader("Authorization") String token);
}


