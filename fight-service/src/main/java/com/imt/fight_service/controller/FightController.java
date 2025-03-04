package com.imt.fight_service.controller;

import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.services.FightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fight")
@RequiredArgsConstructor
public class FightController {

    private final FightService fightService;

    @PostMapping("/start")
    public FightLog startFight(@RequestBody List<MonsterDto> monsters) {
        return fightService.simulateFight(monsters.get(0), monsters.get(1));
    }

    @GetMapping("/history")
    public List<FightLog> getFightHistory() {
        return fightService.getFightHistory();
    }

    @GetMapping("/{id}")
    public FightLog getFightById(@PathVariable Long id) {
        return fightService.getFightById(id);
    }
}
