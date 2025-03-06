package com.imt.fight_service;

import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.repository.FightRepository;
import com.imt.fight_service.services.FightService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour FightServiceApplication.
 */
@SpringBootTest
class FightServiceApplicationTests {

    @MockBean
    private FightRepository fightRepository;

    @MockBean
    private FightService fightService;

    /**
     * Vérifie que l'application démarre correctement.
     */
    @Test
    void contextLoads() {
        assertNotNull(fightService);
    }

    /**
     * Teste la récupération de l'historique des combats.
     */
    @Test
    void testGetFightHistory() {
        FightLog fight1 = new FightLog();
        fight1.setWinner("Team1");
        FightLog fight2 = new FightLog();
        fight2.setWinner("Team2");

        when(fightRepository.findAll()).thenReturn(List.of(fight1, fight2));
        List<FightLog> history = fightService.getFightHistory();

        assertEquals(2, history.size());
        assertEquals("Team1", history.get(0).getWinner());
        assertEquals("Team2", history.get(1).getWinner());
    }

    /**
     * Teste la récupération d'un combat spécifique.
     */
    @Test
    void testGetFightById() {
        FightLog fightLog = new FightLog();
        fightLog.setWinner("Team1");
        when(fightRepository.findById("123")).thenReturn(Optional.of(fightLog));

        Optional<FightLog> result = fightService.getFightById("123");
        assertTrue(result.isPresent());
        assertEquals("Team1", result.get().getWinner());
    }

    /**
     * Teste la simulation d'un combat avec des monstres fictifs.
     */
    @Test
    void testSimulateFight() {
        MonsterDto monster1 = new MonsterDto(1L, "Dragon", 100, 50, 20);
        MonsterDto monster2 = new MonsterDto(2L, "Phoenix", 90, 45, 25);
        List<String> team1 = List.of("1");
        List<String> team2 = List.of("2");

        FightLog fightLog = new FightLog();
        fightLog.setWinner("Dragon");

        when(fightService.simulateFight(any(), any())).thenReturn(fightLog);

        FightLog result = fightService.simulateFight(new FightRequest(team1, team2), "valid_token");
        assertNotNull(result);
        assertEquals("Dragon", result.getWinner());
    }
}

