package com.imt.fight_service.controller;

import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.services.FightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour gérer les combats.
 * 
 * - Démarrer un combat entre deux équipes de trois monstres avec validation
 * - Récupérer l'historique des combats
 * - Rejouer un combat spécifique avec vérification
 */
@RestController
@RequestMapping("/fight")
@RequiredArgsConstructor
public class FightController {

    private final FightService fightService;

    /**
     * Démarrer un combat entre deux équipes de trois monstres.
     * 
     * @param monsters Liste des six monstres (3 par équipe)
     * @return Log du combat avec le résultat et les détails des tours.
     */
    @PostMapping("/start")
    public ResponseEntity<FightLog> startFight(@RequestBody List<MonsterDto> monsters) {
        if (monsters == null || monsters.size() != 6) {
            return ResponseEntity.badRequest().body(null);
        }

        List<MonsterDto> team1 = monsters.subList(0, 3);
        List<MonsterDto> team2 = monsters.subList(3, 6);

        if (team1.stream().anyMatch(m -> m.getHp() <= 0) || team2.stream().anyMatch(m -> m.getHp() <= 0)) {
            return ResponseEntity.badRequest().body(null);
        }

        FightLog fightLog = fightService.simulateTeamFight(team1, team2);
        return ResponseEntity.ok(fightLog);
    }

    /**
     * Récupérer l'historique des combats avec pagination.
     * 
     * @param page Numéro de la page demandée.
     * @param size Nombre d'éléments par page.
     * @return Liste des combats enregistrés paginée.
     */
    @GetMapping("/history")
    public ResponseEntity<List<FightLog>> getFightHistory(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        List<FightLog> history = fightService.getPaginatedFightHistory(page, size);
        return history.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(history);
    }

    /**
     * Rejouer un combat spécifique.
     * 
     * @param id ID du combat.
     * @return Log du combat rejoué ou 404 si non trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FightLog> getFightById(@PathVariable String id) {
        FightLog fightLog = fightService.getFightById(id);
        return fightLog != null ? ResponseEntity.ok(fightLog) : ResponseEntity.notFound().build();
    }
}
