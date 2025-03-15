package com.imt.fight_service.controller;

import com.imt.fight_service.model.Battle;
import com.imt.fight_service.model.FightingMonster;
import com.imt.fight_service.openfeign.MonsterClient;
import com.imt.fight_service.services.CombatService;
import com.imt.fight_service.services.CrudService;
import com.imt.fight_service.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/combat")
public class CombatController {

    @Autowired
    private CombatService combatService;
    @Autowired
    private MonsterClient monsterClient;
    @Autowired
    private CrudService crudService;
    @PostMapping("/startBattle")
    public ResponseEntity<ApiResponse> startBattle(@RequestParam(value = "monster1Id") int monster1Id,
                                                   @RequestParam(value = "monster2Id") int monster2Id) {
        try {
            // Récupérer les deux monstres
            ResponseEntity<FightingMonster> response1 = monsterClient.getMonsterById(monster1Id);
            ResponseEntity<FightingMonster> response2 = monsterClient.getMonsterById(monster2Id);

            if (response1.getStatusCode() != HttpStatus.OK || response2.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("Un ou plusieurs monstres non trouvés.", false));
            }

            FightingMonster fightingMonster1 = response1.getBody();
            FightingMonster fightingMonster2 = response2.getBody();

            if (fightingMonster1 == null || fightingMonster2 == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("Erreur lors de la récupération des monstres", false));
            }

            // Lancer le combat entre les deux monstres
            Battle resultBattle = combatService.startCombat(fightingMonster1, fightingMonster2);


            crudService.addBattles(List.of(resultBattle));

            return ResponseEntity.ok(new ApiResponse("Combat lancé avec succès", true, resultBattle));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors du démarrage du combat: " + e.getMessage(), false));
        }
    }

    // Endpoint pour obtenir tous les combats
    @GetMapping("/battles")
    public ResponseEntity<ApiResponse> getAllBattles() {
        try {
            List<Battle> battles = crudService.getAllBattles();
            return ResponseEntity.ok(new ApiResponse("Liste des combats récupérée avec succès", true, battles));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de la récupération des combats: " + e.getMessage(), false));
        }
    }

    @GetMapping("/battles/{id}")
    public ResponseEntity<ApiResponse> getBattleById(@PathVariable("id") String battleId) {
        try {
            Battle battle = combatService.getBattleById(battleId);
            if (battle == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("Combat non trouvé", false));
            }
            return ResponseEntity.ok(new ApiResponse("Combat récupéré avec succès", true, battle));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de la récupération du combat: " + e.getMessage(), false));
        }
    }


}
