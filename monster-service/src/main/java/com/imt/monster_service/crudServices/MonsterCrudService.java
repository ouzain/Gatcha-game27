
package com.imt.monster_service.crudServices;

import com.imt.monster_service.Model.Monster;
import java.util.List;
import java.util.Optional;

public interface MonsterCrudService {
    List<Monster> getAllMonsters();
    Optional<Monster> getMonsterById(int id);
    List<Monster> addMonsters(List<Monster> monsters);
    void deleteMonster(int id);
    Monster saveMonster(Monster monster);
    Monster summonRandomMonster();
}