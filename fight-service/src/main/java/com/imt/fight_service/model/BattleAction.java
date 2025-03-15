package com.imt.fight_service.model;

public class BattleAction {
    private int turn;
    private Integer monsterId;
    private String skillId;
    private int damage;
    private int targetHp;

    public BattleAction(int turn, Integer monsterId, String skillId, int damage, int targetHp) {
        this.turn = turn;
        this.monsterId = monsterId;
        this.skillId = skillId;
        this.damage = damage;
        this.targetHp = targetHp;
    }

    public int getTurn() {
        return turn;
    }

    public Integer getMonsterId() {
        return monsterId;
    }

    public String getSkillId() {
        return skillId;
    }

    public int getDamage() {
        return damage;
    }

    public int getTargetHp() {
        return targetHp;
    }
}

