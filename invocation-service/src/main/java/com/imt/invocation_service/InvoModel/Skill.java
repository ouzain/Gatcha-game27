package com.imt.invocation_service.InvoModel;

import lombok.*;


@AllArgsConstructor
class Skill {
    private String name;
    private int dmg;
    private Ratio ratio;
    private int cooldown;
    private int levelAmelioration;
    private int levelMax;

    public Skill(){}


    public String getName() {
        return name;
    }

    public int getDmg() {
        return dmg;
    }

    public Ratio getRatio() {
        return ratio;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getLevelAmelioration() {
        return levelAmelioration;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setRatio(Ratio ratio) {
        this.ratio = ratio;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setLevelAmelioration(int levelAmelioration) {
        this.levelAmelioration = levelAmelioration;
    }

    public void setLevelMax(int levelMax) {
        this.levelMax = levelMax;
    }
}
