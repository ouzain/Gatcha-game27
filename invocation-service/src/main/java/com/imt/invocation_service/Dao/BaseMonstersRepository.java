package com.imt.invocation_service.Dao;

import com.imt.invocation_service.Dto.MonsterDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseMonstersRepository extends MongoRepository<MonsterDto, Integer> {
}
