package com.imt.invocation_service.Dao;

import com.imt.invocation_service.InvoModel.BaseMonster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseMonsterRepository extends MongoRepository<BaseMonster, Integer> {
}
