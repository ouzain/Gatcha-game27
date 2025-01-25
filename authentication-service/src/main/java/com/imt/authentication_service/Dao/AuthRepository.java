package com.imt.authentication_service.Dao;

import com.imt.authentication_service.AuthModel.AuthEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends MongoRepository<AuthEntity, String> {

    AuthEntity findByUsername(String username);
    AuthEntity findByToken(String token);
}
