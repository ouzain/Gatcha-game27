package com.imt.fight_service.services;

import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.openFeign.MonsterClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsable de la récupération des monstres via MonsterClient.
 */
@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterClient monsterClient;

    /**
     * Récupère une liste de monstres à partir de leurs IDs.
     */
    public List<MonsterDto> getMonstersByIds(List<String> monsterIds) {
        return monsterIds.stream()
                .map(id -> {
                    try {
                        return monsterClient.getMonsterById(Long.parseLong(id));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid monster ID format: " + id, e);
                    }
                })
                .collect(Collectors.toList());
    }
}
