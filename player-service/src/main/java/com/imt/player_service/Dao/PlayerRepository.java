package com.imt.player_service.Dao;



import com.imt.player_service.Model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

    Player findByUsername(String username);



}
