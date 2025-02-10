package com.imt.invocation_service.InvoModel;

import com.imt.invocation_service.InvoModel.Skill;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "monstre")
public class Monster {

        @Id
        private Integer id;

        private String name;
        private String element; // "feu", "eau", "vent", etc.
        private int hp;
        private int atk;
        private int def;
        private int speed;
        private double invocationRate; // Probabilité d'invocation en %

        private List<Skill> skills;

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
}




