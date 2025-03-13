package com.imt.fight_service.services;

import com.imt.fight_service.dto.FightRequest;
import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.repository.FightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service dédié à la simulation des combats.
 */
@Service
@RequiredArgsConstructor
public class FightSimulationService {

    private final Random random = new Random();
    private final FightRepository fightRepository;
    private final MonsterService monsterService;

    /**
     * Simule un combat basé sur une requête utilisateur.
     */
    public FightLog simulateFight(FightRequest request) {
        List<MonsterDto> team1 = monsterService.getMonstersByIds(request.getTeam1());
        List<MonsterDto> team2 = monsterService.getMonstersByIds(request.getTeam2());

        FightLog fightLog = new FightLog();
        fightLog.setTeam1(team1.stream().map(MonsterDto::getName).toList());
        fightLog.setTeam2(team2.stream().map(MonsterDto::getName).toList());
        fightLog.setFightDate(LocalDateTime.now());

        int turn = 0;
        while (!team1.isEmpty() && !team2.isEmpty() && turn < 50) {
            turn++;
            MonsterDto attacker = selectAttacker(team1, team2);
            MonsterDto defender = selectDefender(attacker, team1, team2);

            if (random.nextDouble() < 0.05) {
                fightLog.addCombatDetail("Tour " + turn + ": " + defender.getName() + " esquive l'attaque de " + attacker.getName() + " !");
                continue;
            }

            if (useSkill(attacker, defender, fightLog, turn)) {
                continue;
            }

            int damage = calculateDamage(attacker, defender);
            defender.setHp(defender.getHp() - damage);
            fightLog.addCombatDetail("Tour " + turn + ": " + attacker.getName() + " attaque " + defender.getName() + " et inflige " + damage + " dégâts.");

            if (defender.getHp() <= 0) {
                fightLog.addCombatDetail(defender.getName() + " est K.O.");
                removeDefeatedMonster(defender, team1, team2);
            }
        }

        fightLog.setWinner(team1.isEmpty() ? "Équipe 2" : "Équipe 1");
        fightLog.setTurns(turn);
        return fightRepository.save(fightLog);
    }

    /**
     * Sélectionne un attaquant basé sur la vitesse.
     */
    public MonsterDto selectAttacker(List<MonsterDto> team1, List<MonsterDto> team2) {
        List<MonsterDto> allMonsters = new ArrayList<>(team1);
        allMonsters.addAll(team2);

        int maxSpeed = allMonsters.stream().mapToInt(MonsterDto::getSpeed).max().orElse(0);
        List<MonsterDto> fastestMonsters = allMonsters.stream().filter(m -> m.getSpeed() == maxSpeed).toList();

        return fastestMonsters.get(random.nextInt(fastestMonsters.size()));
    }

    /**
     * Sélectionne un défenseur dans l'équipe adverse.
     */
    public MonsterDto selectDefender(MonsterDto attacker, List<MonsterDto> team1, List<MonsterDto> team2) {
        List<MonsterDto> opponents = team1.contains(attacker) ? team2 : team1;
        return opponents.get(random.nextInt(opponents.size()));
    }

    /**
     * Calcule les dégâts en prenant en compte les résistances, faiblesses et coups critiques.
     */
    public int calculateDamage(MonsterDto attacker, MonsterDto defender) {
        int baseDamage = Math.max(0, attacker.getAtk() - defender.getDef());
        int typeModifier = getTypeModifier(attacker, defender);

        boolean isCritical = random.nextDouble() < 0.1;
        baseDamage *= typeModifier;
        if (isCritical) {
            baseDamage *= 2;
        }

        return baseDamage;
    }

    /**
     * Vérifie si l'attaque bénéficie d'un bonus ou malus selon le type.
     */
    private int getTypeModifier(MonsterDto attacker, MonsterDto defender) {
        if (defender.getWeaknesses().contains(attacker.getType())) return 2;
        if (defender.getResistances().contains(attacker.getType())) return 0;
        return 1;
    }

    /**
     * Gère l'utilisation des compétences des monstres.
     */
    public boolean useSkill(MonsterDto attacker, MonsterDto defender, FightLog fightLog, int turn) {
        return attacker.getSkills().stream().filter(skill -> skill.isReady()).findFirst().map(skill -> {
            skill.useSkill();
            int skillDamage = skill.getPower();
            defender.setHp(defender.getHp() - skillDamage);
            fightLog.addCombatDetail("Tour " + turn + ": " + attacker.getName() + " utilise " + skill.getName() + " sur " + defender.getName() + " et inflige " + skillDamage + " dégâts.");
            return true;
        }).orElse(false);
    }

    /**
     * Retire un monstre K.O. de son équipe.
     */
    public void removeDefeatedMonster(MonsterDto defeated, List<MonsterDto> team1, List<MonsterDto> team2) {
        team1.removeIf(monster -> monster.equals(defeated));
        team2.removeIf(monster -> monster.equals(defeated));
    }
}
