package com.imt.fight_service.model;


public class Skill {
    private Integer num;
    private int dmg;
    private Ratio ratio;
    private int cooldown;
    private int lvlMax;

    public Skill() {}

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer name) {
        this.num = name;
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



    public static final class Builder {
        private Integer num;
        private int dmg;
        private Ratio ratio;
        private int cooldown;
        private int lvlMax;

        private Builder() {}

        // Méthode statique pour instancier le builder
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
            skill.setNum(this.num);
            skill.setDmg(this.dmg);
            skill.setRatio(this.ratio);
            skill.setCooldown(this.cooldown);
            skill.setLvlMax(this.lvlMax);
            return skill;
        }
    }
}
