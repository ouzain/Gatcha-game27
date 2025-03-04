package com.imt.fight_service.repository;

import com.imt.fight_service.model.FightLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FightRepository extends MongoRepository<FightLog, Long> {
}