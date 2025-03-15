package com.imt.fight_service.services;

import com.imt.fight_service.model.Battle;

import java.util.List;
import java.util.Optional;

public interface CrudService {
    List<Battle> getAllBattles();
    Optional<Battle> getBattleById(String id);
    List<Battle> addBattles(List<Battle> battles);
    void deleteBattle(String id);
    Battle saveBattle(Battle battle);
}
