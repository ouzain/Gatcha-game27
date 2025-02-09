package com.imt.fight_service.services;

import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.repository.FightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FightService {

    private final FightRepository fightRepository;

    public FightLog simulateFight(MonsterDto monster1, MonsterDto monster2) {
        FightLog fightLog = new FightLog();

        fightLog.setMonster1(monster1.getName());
        fightLog.setMonster2(monster2.getName());

        int turn = 0;
        while (monster1.getHp() > 0 && monster2.getHp() > 0) {
            turn++;

            monster2.setHp(monster2.getHp() - Math.max(0, monster1.getAtk() - monster2.getDef()));

            if (monster2.getHp() <= 0) {
                fightLog.setWinner(monster1.getName());
                break;
            }

            monster1.setHp(monster1.getHp() - Math.max(0, monster2.getAtk() - monster1.getDef()));

            if (monster1.getHp() <= 0) {
                fightLog.setWinner(monster2.getName());
                break;
            }
        }

        fightLog.setTurns(turn);
        return fightRepository.save(fightLog);
    }

    public List<FightLog> getFightHistory() {
        return fightRepository.findAll();
    }

    public FightLog getFightById(Long id) {
        return fightRepository.findById(id).orElseThrow(() -> new RuntimeException("Fight not found!"));
    }
}