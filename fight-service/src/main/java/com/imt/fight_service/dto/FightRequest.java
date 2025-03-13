package com.imt.fight_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO représentant une demande de combat.
 * 
 * Contient les identifiants des monstres des deux équipes qui vont s'affronter.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightRequest {
    private List<String> team1; // Identifiants des monstres de l'équipe 1
    private List<String> team2; // Identifiants des monstres de l'équipe 2
}
