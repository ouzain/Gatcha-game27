package com.imt.fight_service.repository;

import com.imt.fight_service.model.FightLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des logs de combat.
 *
 * Permet de récupérer et stocker les combats enregistrés dans MongoDB.
 */
@Repository
public interface FightRepository extends MongoRepository<FightLog, String> {

    /**
     * Récupère tous les logs de combat avec pagination.
     *
     * @param pageable Paramètres de pagination.
     * @return Page contenant les logs de combat.
     */
    Page<FightLog> findAll(Pageable pageable);

    /**
     * Récupère les combats impliquant une équipe spécifique.
     *
     * @param monsterName Nom d'un monstre dans une équipe.
     * @return Liste des combats où ce monstre a participé.
     */
    List<FightLog> findByTeam1ContainingOrTeam2Containing(String monsterName, String monsterName2);
}
