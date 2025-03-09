package com.imt.monster_service.Model;

import com.imt.monster_service.Dto.MonsterDto;
import com.imt.monster_service.Dto.SkillDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "monsters")
public class Monster {
    public enum ElementType {
        FIRE, WATER, WIND
    }

    @Id
    private Integer id;
    // //TODO : réfléchir sur la possibilité d'ajouter un uuid à un monstre pour faciliter la manip
    private ElementType element;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private double lootRate;
    private List<Skill> skills;
    private  int experience;
    private int maxExperience;



    public Monster() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public ElementType getElement() {
        return element;
    }
    public void setElement(ElementType element) {
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
    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getMaxExperience() {
        return maxExperience;
    }
    public void setMaxExperience(int maxExperience) {
        this.maxExperience = maxExperience;
    }
    public static final class Builder {
        private Integer id;
        private ElementType element;
        private int hp;
        private int atk;
        private int def;
        private int vit;
        private double lootRate;
        private List<Skill> skills;
        private int experience;
        private int maxExperience;


        private Builder() {}

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder element(ElementType element) {
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

        public Builder experience(int experience) {
            this.experience = experience;
            return this;
        }
        public Builder maxExperience(int maxExperience) {
            this.maxExperience = maxExperience;
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
            monster.setExperience(this.experience);
            monster.setMaxExperience(this.maxExperience);
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
                .experience(this.experience)
                .maxExperience(this.maxExperience)
                .build();
    }

}
