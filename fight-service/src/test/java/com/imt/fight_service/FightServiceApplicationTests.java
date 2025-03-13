package com.imt.fight_service;

import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.repository.FightRepository;
import com.imt.fight_service.services.FightService;
import com.imt.fight_service.dto.FightRequest; // Import correct de FightRequest
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class) // Utilisation correcte de Mockito avec JUnit 5
class FightServiceApplicationTests {

    @Mock
    private FightRepository fightRepository;

    @Mock
    private FightService fightService;

    /**
     * Vérifie que l'application démarre correctement.
     */
    @Test
    void contextLoads() {
        assertNotNull(fightService, "FightService should not be null");
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

        when(fightService.getFightHistory()).thenReturn(List.of(fight1, fight2)); // Correction ici

        List<FightLog> history = fightService.getFightHistory();

        assertNotNull(history, "History should not be null");
        assertEquals(2, history.size(), "History should contain two fights");
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

        assertTrue(result.isPresent(), "Fight should be present");
        assertEquals("Team1", result.get().getWinner());
    }

    /**
     * Teste la simulation d'un combat avec des monstres fictifs.
     */
    @Test
    void testSimulateFight() {
        MonsterDto monster1 = new MonsterDto(1L, "Dragon", 100, 50, 20, List.of(), List.of(), List.of());
        MonsterDto monster2 = new MonsterDto(2L, "Phoenix", 90, 45, 25, List.of(), List.of(), List.of());
        List<String> team1 = List.of("1");
        List<String> team2 = List.of("2");

        FightLog fightLog = new FightLog();
        fightLog.setWinner("Dragon");

        when(fightService.simulateFight(any(FightRequest.class), anyString())).thenReturn(fightLog);

        FightLog result = fightService.simulateFight(new FightRequest(team1, team2), "valid_token");

        assertNotNull(result, "Fight result should not be null");
        assertEquals("Dragon", result.getWinner());
    }
}
