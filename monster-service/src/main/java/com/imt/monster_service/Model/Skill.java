package com.imt.monster_service.Model;

import com.imt.monster_service.Dto.RatioDto;
import com.imt.monster_service.Dto.SkillDto;

public class Skill {
    private int num;
    private int dmg;
    private Ratio ratio;
    private int cooldown;
    private int lvlMax;

    public Skill() {}

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public Ratio getRatio() {
        return ratio;
    }

    public void setRatio(Ratio ratio) {
        this.ratio = ratio;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getLvlMax() {
        return lvlMax;
    }

    public void setLvlMax(int lvlMax) {
        this.lvlMax = lvlMax;
    }

    /**
     * Convertit cette entité Skill en DTO SkillDto
     */
    public SkillDto toSkillDto() {
        return SkillDto.Builder.builder()
                .num(this.num)
                .dmg(this.dmg)
                .cooldown(this.cooldown)
                .lvlMax(this.lvlMax)
                // Conversion Ratio -> RatioDto (si ratio != null)
                .ratio(this.ratio != null ? this.ratio.toRatioDto() : null)
                .build();
    }

    public static final class Builder {
        private int num;
        private int dmg;
        private Ratio ratio;
        private int cooldown;
        private int lvlMax;

        // Constructeur privé
        private Builder() {}

        // Méthode statique pour instancier le builder
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

        public Builder ratio(Ratio ratio) {
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

        public Skill build() {
            Skill skill = new Skill();
            skill.setNum(this.num);
            skill.setDmg(this.dmg);
            skill.setRatio(this.ratio);
            skill.setCooldown(this.cooldown);
            skill.setLvlMax(this.lvlMax);
            return skill;
        }
    }
}
