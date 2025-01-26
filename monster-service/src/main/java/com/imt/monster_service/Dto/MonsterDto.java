package com.imt.monster_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imt.monster_service.Model.Monster;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonsterDto implements Serializable {

    @JsonProperty("id")
    protected Integer id;

    @JsonProperty("element")
    protected String element;

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
    }

    public Integer getId() {
        return id;
    }

    public String getElement() {
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

    /**
     * Vérifie la validité minimale de ce DTO
     */
    public boolean isValid() {
        return ObjectUtils.allNotNull(element);
    }

    /**
     * Convertit ce DTO en entité Monster
     */
    public Monster toMonsterEntity() {
        return Monster.Builder
                .builder().id(this.id)
                .element(this.element)
                .hp(this.hp)
                .atk(this.atk)
                .def(this.def)
                .vit(this.vit)
                .lootRate(this.lootRate).skills(this.skillDtos.stream()
                        .map(SkillDto::toSkillEntity)
                        .toList())
                .build();
    }

    public static final class Builder {
        private Integer id;
        private String element;
        private int hp;
        private int atk;
        private int def;
        private int vit;
        private double lootRate;
        private List<SkillDto> skillDtos;

        private Builder() {}

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder element(String element) {
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

        public MonsterDto build() {
            return new MonsterDto(this);
        }
    }
}
