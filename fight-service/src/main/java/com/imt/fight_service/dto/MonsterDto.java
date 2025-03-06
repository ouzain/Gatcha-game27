package com.imt.fight_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO représentant un monstre dans le système.
 * 
 * Ce modèle est utilisé pour échanger des données sur les monstres entre les services ou via l'API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonsterDto {
    private Long id;
    private String name;
    private int hp;
    private int atk;
    private int def;
    private int speed;
    private String type;
    private List<SkillDto> skills;
    private List<String> resistances;
    private List<String> weaknesses;

    // 🔥 Ajout des attributs manquants pour éviter les erreurs
    private int energy; // Gestion de l'énergie
    private boolean fatigued; // État de fatigue
    private int attackBoost; // Boost temporaire d'attaque
    private boolean shielded; // Protection activée

    /**
     * Calcul des dégâts en prenant en compte les compétences, la défense et les types.
     */
    public int calculateDamage(MonsterDto opponent) {
        int baseDamage = Math.max(0, this.atk - opponent.getDef());
        int skillBonus = skills.stream().filter(SkillDto::isReady).mapToInt(SkillDto::getPower).sum();
        int typeModifier = getTypeModifier(opponent);
        return (baseDamage + skillBonus) * typeModifier;
    }

    /**
     * Détermine le multiplicateur de dégâts en fonction des types de monstres.
     */
    private int getTypeModifier(MonsterDto opponent) {
        if (this.getType() == null || opponent.getType() == null) return 1;
        if (this.getSkills().stream().anyMatch(skill -> opponent.getWeaknesses().contains(skill.getName()))) {
            return 2; // Double dégâts si la compétence exploite une faiblesse
        }
        if (opponent.getResistances().contains(this.getType())) {
            return 0; // Pas de dégâts si le monstre est immunisé
        }
        return 1;
    }
}
