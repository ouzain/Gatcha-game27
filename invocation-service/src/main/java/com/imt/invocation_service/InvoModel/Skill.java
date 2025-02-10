package com.imt.invocation_service.InvoModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Skill {
    private String nom;
    private int degatsBase;
    private double ratioDegatsSupp;
    private String statAssocieted; // hp, atk, def, vit
    private int cooldown;
    private int levelAmelioration;
    private int levelMax;
}
