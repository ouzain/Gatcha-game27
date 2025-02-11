package com.imt.fight_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fighterlogs")
@Data
@NoArgsConstructor
public class FightLog {
    @Id
    private Long id;

    private String winner;
    private String monster1;
    private String monster2;
    private int turns;
    private String details;
}