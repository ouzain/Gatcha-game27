package com.imt.invocation_service.Dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonsterDto implements Serializable {

    public enum ElementType {
        FIRE, WATER, WIND
    }

    @JsonProperty("id")
    protected Integer id;

    @JsonProperty("element")
    protected ElementType element;

    @JsonProperty("hp")
    protected int hp;

    @JsonProperty("atk")
    protected int atk;

    @JsonProperty("def")
    protected int def;

    @JsonProperty("vit")
    protected int vit;

    @JsonProperty("loot_rate")
    protected double lootRate;

    @JsonProperty("skillDtos")
    protected List<SkillDto> skillDtos;

    @JsonProperty("experience")
    protected int experience;
    @JsonProperty("maxExperience")
    protected int maxExperience;


    /**
     * Constructeur protégé (pour la sérialisation JSON)
     */
    protected MonsterDto() {
        super();
    }

    private MonsterDto(Builder builder) {
        this.id = builder.id;
        this.element = builder.element;
        this.hp = builder.hp;
        this.atk = builder.atk;
        this.def = builder.def;
        this.vit = builder.vit;
        this.lootRate = builder.lootRate;
        this.skillDtos = builder.skillDtos;
        this.experience = builder.experience;
        this.maxExperience = builder.maxExperience;


    }

    public Integer getId() {
        return id;
    }

    public ElementType getElement() {
        return element;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getVit() {
        return vit;
    }

    public double getLootRate() {
        return lootRate;
    }

    public List<SkillDto> getSkillDtos() {
        return skillDtos;
    }
    public int getExperience() {
        return experience;
    }
    public int getMaxExperience() {
        return maxExperience;
    }


    /**
     * Convertit ce DTO en entité Monster
     */


    public static final class Builder {
        private Integer id;
        private ElementType element;
        private int hp;
        private int atk;
        private int def;
        private int vit;
        private double lootRate;
        private List<SkillDto> skillDtos;
        private int experience;
        private int maxExperience;

        private Builder() {}

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder element(ElementType element) {
            this.element = element;
            return this;
        }

        public Builder hp(int hp) {
            this.hp = hp;
            return this;
        }

        public Builder atk(int atk) {
            this.atk = atk;
            return this;
        }

        public Builder def(int def) {
            this.def = def;
            return this;
        }

        public Builder vit(int vit) {
            this.vit = vit;
            return this;
        }

        public Builder lootRate(double lootRate) {
            this.lootRate = lootRate;
            return this;
        }

        public Builder skillDtos(List<SkillDto> skillDtos) {
            this.skillDtos = skillDtos;
            return this;
        }
        public Builder experience(int experience) {
            this.experience = experience;
            return this;
        }
        public Builder maxExperience(int maxExperience) {
            this.maxExperience = maxExperience;
            return this;
        }

        public MonsterDto build() {
            return new MonsterDto(this);
        }
    }
}