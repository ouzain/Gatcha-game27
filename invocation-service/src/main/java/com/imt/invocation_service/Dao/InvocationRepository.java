package com.imt.invocation_service.Dao;


import com.imt.invocation_service.InvoModel.Invocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvocationRepository extends MongoRepository<Invocation, Integer> {
}

