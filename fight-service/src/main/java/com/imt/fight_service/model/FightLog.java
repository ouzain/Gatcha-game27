package com.imt.fight_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modèle représentant un combat dans l'historique.
 * 
 * Contient les détails du combat : monstres impliqués, vainqueur, nombre de tours et logs des actions.
 */
@Document(collection = "fight_logs")
@Data
@NoArgsConstructor
public class FightLog {
    @Id
    private String id;
    private String winner;
    private List<String> team1;
    private List<String> team2;
    private int turns;
    private List<String> combatDetails;
    private LocalDateTime fightDate;
    private int totalDamageDealt;
    private int totalHealingDone;
    private int totalCriticalHits;
    private int totalMissedAttacks;
    private int totalStatusEffectsApplied;
    private Map<String, MonsterPerformance> monsterPerformances = new HashMap<>();
    
    /**
     * Ajoute une action au log du combat.
     */
    public void addCombatDetail(String detail) {
        this.combatDetails.add(detail);
    }

    /**
     * Met à jour les statistiques du combat après chaque tour.
     */
    public void updateStatistics(int damageDealt, int healingDone, int criticalHits, int missedAttacks, int statusEffectsApplied) {
        this.totalDamageDealt += damageDealt;
        this.totalHealingDone += healingDone;
        this.totalCriticalHits += criticalHits;
        this.totalMissedAttacks += missedAttacks;
        this.totalStatusEffectsApplied += statusEffectsApplied;
    }

    /**
     * Met à jour les performances individuelles des monstres.
     */
    public void updateMonsterPerformance(String monsterName, int damageDealt, int healingDone, int criticalHits, int missedAttacks, int statusEffectsApplied) {
        monsterPerformances.putIfAbsent(monsterName, new MonsterPerformance());
        MonsterPerformance performance = monsterPerformances.get(monsterName);
        performance.addStats(damageDealt, healingDone, criticalHits, missedAttacks, statusEffectsApplied);
    }

    /**
     * Classe interne pour stocker les performances individuelles des monstres.
     */
    @Data
    @NoArgsConstructor
    public static class MonsterPerformance {
        private int damageDealt = 0;
        private int healingDone = 0;
        private int criticalHits = 0;
        private int missedAttacks = 0;
        private int statusEffectsApplied = 0;

        public void addStats(int damageDealt, int healingDone, int criticalHits, int missedAttacks, int statusEffectsApplied) {
            this.damageDealt += damageDealt;
            this.healingDone += healingDone;
            this.criticalHits += criticalHits;
            this.missedAttacks += missedAttacks;
            this.statusEffectsApplied += statusEffectsApplied;
        }
    }
}


