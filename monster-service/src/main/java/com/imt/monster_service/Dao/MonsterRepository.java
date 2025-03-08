package com.imt.monster_service.Dao;



import com.imt.monster_service.Model.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonsterRepository extends MongoRepository<Monster, Integer> {

    Optional<Monster> findById(int id);

    List<Monster> findAllByTokenUser(String token);
}
