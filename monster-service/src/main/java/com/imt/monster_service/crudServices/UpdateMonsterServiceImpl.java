package com.imt.monster_service.crudServices;

import com.imt.monster_service.Dao.MonsterRepository;
import com.imt.monster_service.Model.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "updateService")
public class UpdateMonsterServiceImpl implements UpdateMonsterService{
    @Autowired
    private MonsterRepository monsterRepository;
    @Override
    public void execute(Monster monster) {
       Optional<Monster> foundMonster =monsterRepository.findById(monster.getId());
       Monster updatedMonster = Monster.Builder.builder()
               .id(foundMonster.get().getId())
               .element(monster.getElement())
               .atk(monster.getAtk())
               .def(monster.getDef())
               .vit((monster.getVit()))
               .skills(monster.getSkills())
               .lootRate(monster.getLootRate())
               .build();

       monsterRepository.save(updatedMonster);
    }


    @Override
    public void addExp(Integer id, int exp) {
        Optional<Monster> monsterOptional    = monsterRepository.findById(id);
        if (monsterOptional.isPresent()) {
            Monster monster = monsterOptional.get();
            monster.setHp(monster.getHp() + exp);
            monster.setAtk(monster.getAtk() + exp);
            monster.setVit(monster.getVit() + exp);
            monster.setDef(monster.getDef() + exp);
            monster.setLootRate(monster.getLootRate() + exp);
        }

    }


}
