package com.imt.fight_service.repository;

import com.imt.fight_service.model.FightLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FightRepository extends JpaRepository<FightLog, Long> {
}