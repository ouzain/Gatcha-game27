package com.imt.fight_service.dao;

import com.imt.fight_service.model.Battle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BattleRepository extends MongoRepository<Battle, String> {
    Optional<Battle> findById(String id);
    List<Battle> findAll();


}

