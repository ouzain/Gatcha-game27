package com.imt.invocation_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillDto implements Serializable {

    @JsonProperty("num")
    protected Integer num;

    @JsonProperty("dmg")
    protected int dmg;

    @JsonProperty("ratio")  // On mappe RatioDto
    protected RatioDto ratio;

    @JsonProperty("cooldown")
    protected int cooldown;

    @JsonProperty("lvl_max")
    protected int lvlMax;

    /**
     * Constructeur protégé pour Jackson
     */
    protected SkillDto() {
        super();
    }

    private SkillDto(Builder builder) {
        this.num = builder.num;
        this.dmg = builder.dmg;
        this.ratio = builder.ratio;
        this.cooldown = builder.cooldown;
        this.lvlMax = builder.lvlMax;
    }

    public Integer getnum() {
        return num;
    }

    public int getDmg() {
        return dmg;
    }

    public RatioDto getRatio() {
        return ratio;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getLvlMax() {
        return lvlMax;
    }

    /**
     * Validation minimale (exemple)
     */


    public static final class Builder {
        private Integer num;
        private int dmg;
        private RatioDto ratio;
        private int cooldown;
        private int lvlMax;

        private Builder() {}

        public static Builder builder() {
            return new Builder();
        }

        public Builder num(Integer num) {
            this.num = num;
            return this;
        }

        public Builder dmg(int dmg) {
            this.dmg = dmg;
            return this;
        }

        public Builder ratio(RatioDto ratio) {
            this.ratio = ratio;
            return this;
        }

        public Builder cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public Builder lvlMax(int lvlMax) {
            this.lvlMax = lvlMax;
            return this;
        }

        public SkillDto build() {
            return new SkillDto(this);
        }
    }
}
