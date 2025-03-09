package com.imt.monster_service.crudServices;

import com.imt.monster_service.Dao.MonsterRepository;
import com.imt.monster_service.Model.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service(value = "monster-service")
public class MonsterCrudServiceImpl implements MonsterCrudService {

    @Autowired
    private MonsterRepository monsterRepository;

    @Override
    public List<Monster> getAllMonsters() {
        return monsterRepository.findAll();
    }

    @Override
    public Optional<Monster> getMonsterById(int id) {
        return monsterRepository.findById(id);
    }

    @Override
    public List<Monster> addMonsters(List<Monster> monsters) {
        return monsterRepository.saveAll(monsters);
    }

    @Override
    public void deleteMonster(int id) {
        monsterRepository.deleteById(id);
    }
    @Override
    public Monster saveMonster(Monster monster) {
        return monsterRepository.save(monster);
    }

    @Override
    public Monster summonRandomMonster() {
        List<Monster> monsters = monsterRepository.findAll();
        if (monsters.isEmpty()) {
            throw new RuntimeException("No monsters available in the database");
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(monsters.size());
        return monsters.get(randomIndex);
    }
}