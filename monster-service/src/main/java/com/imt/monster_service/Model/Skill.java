package com.imt.monster_service.Model;

import com.imt.monster_service.Dto.SkillDto;

public class Skill {
    private String name;
    private int dmg;
    private Ratio ratio;
    private int cooldown;
    private int lvlMax;

    public Skill() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setRatio(Ratio ratio) {
        this.ratio = ratio;
    }

    public Ratio getRatio() {
        return ratio;
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
                .name(this.name)
                .dmg(this.dmg)
                .cooldown(this.cooldown)
                .lvlMax(this.lvlMax)
                // Conversion Ratio -> RatioDto (si ratio != null)
                .build();
    }

    public static final class Builder {
        private String name;
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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dmg(int dmg) {
            this.dmg = dmg;
            return this;
        }

        public Builder ratio(Ratio ratio){
            this.ratio=ratio;
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
            skill.setName(this.name);
            skill.setDmg(this.dmg);
            skill.setRatio(this.ratio);
            skill.setCooldown(this.cooldown);
            skill.setLvlMax(this.lvlMax);
            return skill;
        }
    }
}
