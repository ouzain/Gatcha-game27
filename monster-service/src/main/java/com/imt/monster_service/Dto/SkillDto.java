package com.imt.monster_service.Dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imt.monster_service.Model.Skill;


import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillDto implements Serializable {

    @JsonProperty("num")
    protected int num;

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

    public int getNum() {
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
    public boolean isValid() {
        // On vérifie juste que ratio n'est pas null,
        // et si ratio != null, alors ratio.isValid().
        return this.ratio == null
                || (this.ratio != null && this.ratio.isValid());
    }

    public Skill toSkillEntity() {
        return Skill.Builder
                .builder()
                .num(this.num)
                .dmg(this.dmg)
                .ratio(this.ratio.toRatioEntity())
                .cooldown(this.cooldown)
                .lvlMax(this.lvlMax)
                .build();
    }

    public static final class Builder {
        private int num;
        private int dmg;
        private RatioDto ratio;
        private int cooldown;
        private int lvlMax;

        private Builder() {}

        public static Builder builder() {
            return new Builder();
        }

        public Builder num(int num) {
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
