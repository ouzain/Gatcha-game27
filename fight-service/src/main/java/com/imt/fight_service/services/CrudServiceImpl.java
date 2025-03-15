package com.imt.fight_service.services;

import com.imt.fight_service.model.Battle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imt.fight_service.dao.BattleRepository;

import java.util.List;
import java.util.Optional;

@Service(value = "crudService")
public class CrudServiceImpl implements CrudService {
   @Autowired
   private BattleRepository battleRepository;

   @Override
   public List<Battle> getAllBattles() {
      return battleRepository.findAll();
   }

   @Override
   public Optional<Battle> getBattleById(String id) {
      return battleRepository.findById(id);
   }

   @Override
   public List<Battle> addBattles(List<Battle> battles) {
      return battleRepository.saveAll(battles);
   }

   @Override
   public void deleteBattle(String id) {
      battleRepository.deleteById(id);
   }
   @Override
   public Battle saveBattle(Battle battle) {
      return battleRepository.save(battle);
   }
}
