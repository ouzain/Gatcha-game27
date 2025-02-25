package com.imt.fight_service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
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
class FightController {
    @Autowired
    private FightService fightService;

    @PostMapping("/simulate")
    public FightResult simulateFight(@RequestBody FightRequest request, @RequestHeader("Authorization") String token) {
        return fightService.simulateFight(request, token);
    }

    @GetMapping("/history")
    public List<FightResult> getFightHistory() {
        return fightService.getFightHistory();
    }

    @GetMapping("/replay/{fightId}")
    public Optional<FightResult> replayFight(@PathVariable String fightId) {
        return fightService.getFightById(fightId);
    }
}

@Service
class FightService {
    @Autowired
    private FightRepository fightRepository;
    @Autowired
    private RestTemplate restTemplate;

    public FightResult simulateFight(FightRequest request, String token) {
        if (!validateToken(token)) {
            throw new RuntimeException("Unauthorized");
        }

        Monster monster1 = restTemplate.getForObject("http://monster-service/monsters/" + request.getMonster1(), Monster.class);
        Monster monster2 = restTemplate.getForObject("http://monster-service/monsters/" + request.getMonster2(), Monster.class);

        String winner = fightLogic(monster1, monster2);

        FightResult result = new FightResult(UUID.randomUUID().toString(), winner);
        fightRepository.save(result);
        return result;
    }

    public List<FightResult> getFightHistory() {
        return fightRepository.findAll();
    }

    public Optional<FightResult> getFightById(String fightId) {
        return fightRepository.findById(fightId);
    }

    private boolean validateToken(String token) {
        return restTemplate.getForObject("http://auth-service/validate?token=" + token, Boolean.class);
    }

    private String fightLogic(Monster monster1, Monster monster2) {
        return monster1.getAtk() > monster2.getAtk() ? monster1.getId() : monster2.getId();
    }
}

interface FightRepository extends MongoRepository<FightResult, String> {}

class FightRequest {
    private String monster1;
    private String monster2;

    public String getMonster1() { return monster1; }
    public void setMonster1(String monster1) { this.monster1 = monster1; }

    public String getMonster2() { return monster2; }
    public void setMonster2(String monster2) { this.monster2 = monster2; }
}

class FightResult {
    @Id
    private String fightId;
    private String winner;

    public FightResult(String fightId, String winner) {
        this.fightId = fightId;
        this.winner = winner;
    }

    public String getFightId() { return fightId; }
    public String getWinner() { return winner; }
}

class Monster {
    private String id;
    private int atk;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }
}
