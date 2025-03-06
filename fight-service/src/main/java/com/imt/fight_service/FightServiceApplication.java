package com.imt.fight_service;

import com.imt.fight_service.dto.FightRequest;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.repository.FightRepository;
import com.imt.fight_service.services.FightService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Point d'entrée principal de l'application FightService.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FightServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FightServiceApplication.class, args);
    }
}

@Configuration
class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@RestController
@RequestMapping("/fight")
@RequiredArgsConstructor
class FightController {
    private final FightService fightService;

    @PostMapping("/simulate")
    public FightLog simulateFight(@RequestBody FightRequest request) {
        return fightService.simulateFight(request);
    }

    @GetMapping("/history")
    public List<FightLog> getFightHistory() {
        return fightService.getFightHistory();
    }

    @GetMapping("/replay/{fightId}")
    public Optional<FightLog> replayFight(@PathVariable String fightId) {
        return fightService.getFightById(fightId);
    }
}

@Service
@RequiredArgsConstructor
class FightService {
    private final FightRepository fightRepository;

    public FightLog simulateFight(FightRequest request) {
        // Implémentation de la simulation du combat
        return new FightLog();
    }

    public List<FightLog> getFightHistory() {
        return fightRepository.findAll();
    }

    public Optional<FightLog> getFightById(String fightId) {
        return fightRepository.findById(fightId);
    }
}
