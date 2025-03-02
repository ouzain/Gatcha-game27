package com.imt.fight_service.services;

import com.imt.fight_service.dto.MonsterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Classe contenant la logique avancée des combats.
 * 
 * Implémente une IA stratégique et des systèmes avancés de combat.
 */
@Service
@RequiredArgsConstructor
public class FightLogic {

    private final Random random = new Random();

    /**
     * Détermine le meilleur monstre attaquant en fonction de la vitesse et de l'état du combat.
     */
    public MonsterDto selectBestAttacker(List<MonsterDto> team) {
        return team.stream()
                .filter(monster -> monster.getHp() > 0)
                .max((m1, m2) -> Integer.compare(m1.getSpeed(), m2.getSpeed()))
                .orElse(null);
    }

    /**
     * Sélectionne la meilleure cible en fonction des faiblesses et du niveau de vie.
     */
    public MonsterDto selectBestTarget(MonsterDto attacker, List<MonsterDto> opponents) {
        return opponents.stream()
                .filter(monster -> monster.getHp() > 0)
                .min((m1, m2) -> compareTargetPriority(attacker, m1, m2))
                .orElse(null);
    }

    /**
     * Compare la priorité entre deux cibles potentielles en fonction des faiblesses et des HP restants.
     */
    private int compareTargetPriority(MonsterDto attacker, MonsterDto m1, MonsterDto m2) {
        int score1 = getTargetPriority(attacker, m1);
        int score2 = getTargetPriority(attacker, m2);
        return Integer.compare(score1, score2);
    }

    /**
     * Évalue une cible en fonction de ses faiblesses et de sa vulnérabilité.
     */
    private int getTargetPriority(MonsterDto attacker, MonsterDto target) {
        int priority = target.getHp();
        if (target.getWeaknesses().contains(attacker.getType())) {
            priority -= 20; // Priorité plus élevée si la cible est faible
        }
        return priority;
    }

    /**
     * Applique une IA avancée pour l'utilisation des compétences.
     */
    public boolean shouldUseSkill(MonsterDto attacker) {
        return attacker.getSkills().stream()
                .filter(skill -> skill.isReady())
                .anyMatch(skill -> random.nextDouble() < 0.7); // 70% de chances d'utilisation
    }

    /**
     * Implémente un système de gestion d'énergie pour limiter les attaques spéciales.
     */
    public boolean hasEnoughEnergy(MonsterDto attacker) {
        return attacker.getEnergy() >= 10; // Nécessite 10 points d'énergie
    }

    /**
     * Réduit l'énergie du monstre après l'utilisation d'une compétence.
     */
    public void consumeEnergy(MonsterDto attacker) {
        attacker.setEnergy(attacker.getEnergy() - 10);
    }

    /**
     * Applique des états de combat : fatigue, boost temporaire, protection.
     */
    public void applyBattleStates(MonsterDto monster) {
        if (random.nextDouble() < 0.2) { // 20% de chances d'appliquer un état
            monster.setFatigued(true);
        }
        if (random.nextDouble() < 0.15) { // 15% de chances d'obtenir un boost temporaire
            monster.setAttackBoost(10);
        }
        if (random.nextDouble() < 0.1) { // 10% de chances d'activer une protection
            monster.setShielded(true);
        }
    }
}

