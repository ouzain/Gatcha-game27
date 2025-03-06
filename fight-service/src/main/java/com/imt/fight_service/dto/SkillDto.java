package com.imt.fight_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO représentant une compétence.
 * 
 * Chaque compétence a un nom, une puissance, un cooldown et des effets spéciaux.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto {
    private String name;
    private int power;
    private int cooldown;
    private int currentCooldown;
    private String type;
    private boolean isAoe; // True si la compétence touche plusieurs ennemis
    private String statusEffect; // Empoisonné, Paralysé, Brûlé, etc.
    private int statusEffectChance; // Pourcentage de chance d'appliquer l'effet

    /**
     * Réduit le cooldown de la compétence à chaque tour.
     */
    public void reduceCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    /**
     * Vérifie si la compétence est prête à être utilisée.
     */
    public boolean isReady() {
        return currentCooldown == 0;
    }

    /**
     * Réinitialise le cooldown après utilisation.
     */
    public void useSkill() {
        currentCooldown = cooldown;
    }

    /**
     * Vérifie si la compétence applique un effet de statut.
     */
    public boolean appliesStatusEffect() {
        return statusEffect != null && !statusEffect.isEmpty();
    }
}

