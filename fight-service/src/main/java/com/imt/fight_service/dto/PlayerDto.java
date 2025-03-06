package com.imt.fight_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO représentant un joueur dans le système.
 * 
 * Ce modèle transporte les données du joueur pour les combats et la gestion de l’équipe.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private Long id;
    private String username;
    private String token;
    private int level;
    private int experience;
    private List<Long> monsterIds;
    private int ranking;
    private int victories;
    private int defeats;
    private List<String> inventory;

    /**
     * Met à jour l'expérience et le niveau du joueur après un combat gagné.
     */
    public void gainExperience(int exp) {
        this.experience += exp;
        if (this.experience >= level * 100) {
            this.level++;
            this.experience = 0;
        }
    }

    /**
     * Met à jour le classement du joueur en fonction du résultat du combat.
     */
    public void updateRanking(boolean isVictory) {
        if (isVictory) {
            victories++;
            ranking += 10;
        } else {
            defeats++;
            ranking = Math.max(0, ranking - 5);
        }
    }

    /**
     * Ajoute un objet à l'inventaire du joueur.
     */
    public void addItemToInventory(String item) {
        inventory.add(item);
    }
}


