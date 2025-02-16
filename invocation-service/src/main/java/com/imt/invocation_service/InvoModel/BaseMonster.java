package com.imt.invocation_service.InvoModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "base_monsters")
public class BaseMonster {

    @Id
    private Integer id;
    private String name;
    private String element; // "feu", "eau", "vent", etc.
    private int hp;
    private int atk;
    private int def;
    private int speed;
    private double invocationRate; // probabilité d'invocation en %
    private List<Skill> skills; // Vos compétences de base

    public BaseMonster() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getInvocationRate() {
        return invocationRate;
    }

    public void setInvocationRate(double invocationRate) {
        this.invocationRate = invocationRate;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    /**
     * Builder pour la classe BaseMonster
     */
    public static class Builder {
        private Integer id;
        private String name;
        private String element;
        private int hp;
        private int atk;
        private int def;
        private int speed;
        private double invocationRate;
        private List<Skill> skills;

        public Builder() {
        }

        // Méthodes de construction chaînées
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
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

        public Builder speed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder invocationRate(double invocationRate) {
            this.invocationRate = invocationRate;
            return this;
        }

        public Builder skills(List<Skill> skills) {
            this.skills = skills;
            return this;
        }

        /**
         * Construit et retourne une instance de BaseMonster
         */
        public BaseMonster build() {
            BaseMonster monster = new BaseMonster();
            monster.setId(this.id);
            monster.setName(this.name);
            monster.setElement(this.element);
            monster.setHp(this.hp);
            monster.setAtk(this.atk);
            monster.setDef(this.def);
            monster.setSpeed(this.speed);
            monster.setInvocationRate(this.invocationRate);
            monster.setSkills(this.skills);
            return monster;
        }
    }
}
