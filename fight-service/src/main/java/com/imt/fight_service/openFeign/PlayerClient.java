package com.imt.fight_service.openFeign;

import com.imt.fight_service.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client Feign pour interagir avec le service des joueurs.
 * 
 * Utilisé pour récupérer les informations des joueurs depuis le microservice player-service.
 */
@FeignClient(name = "player-service-client", url = "http://localhost:8082")
public interface PlayerClient {

    /**
     * Récupère les détails d'un joueur spécifique via son ID.
     * 
     * @param id Identifiant du joueur.
     * @return PlayerDto contenant les statistiques et l’équipe du joueur.
     */
    @GetMapping("/player/{id}")
    PlayerDto getPlayerById(@PathVariable("id") Long id);
}
