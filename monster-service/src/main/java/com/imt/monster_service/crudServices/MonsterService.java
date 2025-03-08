package com.imt.monster_service.crudServices;

import com.imt.monster_service.Dao.MonsterRepository;
import com.imt.monster_service.Model.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service(value = "monster-service")
public class MonsterService {

    @Autowired
    private MonsterRepository monsterRepository;

    public List<Monster> getAllMonsters(String token) {
        return monsterRepository.findAllByTokenUser(token);
    }

    public Optional<Monster> getMonsterById(int id) {
        return monsterRepository.findById(id);
    }

    public Monster addMonster(Monster monster) {
        return monsterRepository.save(monster);
    }

    public void deleteMonster(int id) {
        monsterRepository.deleteById(id);
    }
}
