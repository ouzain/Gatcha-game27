package com.imt.fight_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "battles")
public class Battle {


    private String id;
    private Integer monster1Id;
    private Integer monster2Id;
    private Integer winnerId;
    private List<BattleAction> actions;
    private String date;

    private Battle(Builder builder) {
        this.id = builder.id;
        this.monster1Id = builder.monster1Id;
        this.monster2Id = builder.monster2Id;
        this.winnerId = builder.winnerId;
        this.actions = builder.actions;
        this.date = builder.date;
    }

    public Battle() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Integer getMonster1Id() {
        return monster1Id;
    }

    public Integer getMonster2Id() {
        return monster2Id;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public List<BattleAction> getActions() {
        return actions;
    }

    public String getDate() {
        return date;
    }

    public static class Builder {
        private String id;
        private Integer monster1Id;
        private Integer monster2Id;
        private Integer winnerId;
        private List<BattleAction> actions;
        private String date;

        public Builder() {
            this.actions = new ArrayList<>();
            this.id = UUID.randomUUID().toString();
        }

        public Builder withMonster1Id(Integer monster1Id) {
            this.monster1Id = monster1Id;
            return this;
        }

        public Builder withMonster2Id(Integer monster2Id) {
            this.monster2Id = monster2Id;
            return this;
        }

        public Builder withWinnerId(Integer winnerId) {
            this.winnerId = winnerId;
            return this;
        }

        public Builder withActions(List<BattleAction> actions) {
            this.actions = actions;
            return this;
        }

        public Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public Battle build() {
            return new Battle(this);
        }
    }
}
