package com.imt.fight_service.openFeign;

import com.imt.fight_service.dto.MonsterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * Client Feign pour interagir avec le service des monstres.
 * 
 * Utilisé pour récupérer les informations des monstres depuis le microservice monster-service.
 */
@FeignClient(name = "monster-service-client", url = "http://localhost:8083")
public interface MonsterClient {

    /**
     * Récupère les détails d'un monstre spécifique via son ID.
     * 
     * @param id Identifiant du monstre.
     * @return MonsterDto contenant les statistiques et les compétences du monstre.
     */
    @GetMapping("/monsters/{id}")
    MonsterDto getMonsterById(@PathVariable("id") Long id);

    /**
     * Récupère les détails de plusieurs monstres en une seule requête.
     * 
     * @param ids Liste des identifiants des monstres.
     * @return Liste de MonsterDto correspondant aux identifiants fournis.
     */
    @GetMapping("/monsters/batch")
    List<MonsterDto> getMonstersByIds(@RequestParam List<Long> ids);
}
