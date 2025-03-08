package com.imt.monster_service.Model;

import com.imt.monster_service.Dto.MonsterDto;
import com.imt.monster_service.Dto.SkillDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "monsters")
public class Monster {

    @Id
    private Integer id;
    // id pour faciliter la mani
    private String tokenUser;
    private String name;
    private String element;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private double lootRate;
    private List<Skill> skills;

    public Monster() {}


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public int getVit() {
        return vit;
    }
    public void setVit(int vit) {
        this.vit = vit;
    }

    public double getLootRate() {
        return lootRate;
    }
    public void setLootRate(double lootRate) {
        this.lootRate = lootRate;
    }

    public List<Skill> getSkills() {
        return skills;
    }
    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public static final class Builder {
        private Integer id;
        private String element;
        private int hp;
        private int atk;
        private int def;
        private int vit;
        private double lootRate;
        private List<Skill> skills;

        // Constructeur privé
        private Builder() {}

        // Méthode statique d'entrée
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

        public Builder skills(List<Skill> skills) {
            this.skills = skills;
            return this;
        }

        // Méthode build qui crée l'instance finale
        public Monster build() {
            Monster monster = new Monster();
            monster.setId(this.id);
            monster.setElement(this.element);
            monster.setHp(this.hp);
            monster.setAtk(this.atk);
            monster.setDef(this.def);
            monster.setVit(this.vit);
            monster.setLootRate(this.lootRate);
            monster.setSkills(this.skills);
            return monster;
        }
    }


    public MonsterDto toMonsterDto() {
        List<SkillDto> skillDtos = null;
        if (this.skills != null) {
            skillDtos = this.skills.stream()
                    .map(Skill::toSkillDto)
                    .toList();
        }

        return MonsterDto.Builder.builder()
                .id(this.id)
                .element(this.element)
                .hp(this.hp)
                .atk(this.atk)
                .def(this.def)
                .vit(this.vit)
                .lootRate(this.lootRate)
                .skillDtos(skillDtos)
                .build();
    }

}
