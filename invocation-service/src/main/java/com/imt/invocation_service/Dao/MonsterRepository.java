package com.imt.invocation_service.Dao;

import com.imt.invocation_service.InvoModel.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MonsterRepository extends MongoRepository<Monster, Integer> {
}
