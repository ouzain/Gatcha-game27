package com.imt.fight_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonsterDto {
    private Long id;
    private String name;
    private int hp;
    private int atk;
    private int def;
}
