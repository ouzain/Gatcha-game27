package com.imt.fight_service.services;

import com.imt.fight_service.dto.FightRequest;
import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.exception.FightException;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.repository.FightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service principal pour gérer les combats.
 */
@Service
@RequiredArgsConstructor
public class FightService {

    private final FightRepository fightRepository;
    private final FightSimulationService fightSimulationService;
    private final MonsterService monsterService;

    /**
     * Simule un combat basé sur une requête utilisateur.
     */
    public FightLog simulateFight(FightRequest request) {
        if (request.getTeam1() == null || request.getTeam2() == null || request.getTeam1().isEmpty() || request.getTeam2().isEmpty()) {
            throw new FightException("Les équipes doivent contenir des monstres.");
        }

        return fightSimulationService.simulateFight(request);
    }

    /**
     * Simule un combat entre deux équipes de monstres.
     */
    public FightLog simulateTeamFight(List<MonsterDto> team1, List<MonsterDto> team2) {
        if (team1 == null || team2 == null || team1.isEmpty() || team2.isEmpty()) {
            throw new FightException("Les équipes doivent contenir des monstres.");
        }

        FightRequest request = new FightRequest(
                team1.stream().map(monster -> String.valueOf(monster.getId())).collect(Collectors.toList()),
                team2.stream().map(monster -> String.valueOf(monster.getId())).collect(Collectors.toList())
        );

        return fightSimulationService.simulateFight(request);
    }

    /**
     * Récupère l'historique des combats avec pagination.
     */
    public List<FightLog> getPaginatedFightHistory(int page, int size) {
        return fightRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    /**
     * Récupère un combat spécifique par son ID.
     */
    public Optional<FightLog> getFightById(String fightId) {
        return fightRepository.findById(fightId);
    }
}
