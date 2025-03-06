package com.imt.fight_service.services;

import com.imt.fight_service.dto.MonsterDto;
import com.imt.fight_service.model.FightLog;
import com.imt.fight_service.repository.FightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service principal gérant la logique des combats.
 * Implémente un combat 3v3 avec gestion des priorités, des compétences et des statistiques.
 */
@Service
@RequiredArgsConstructor
public class FightService {

    private final FightRepository fightRepository;
    private final Random random = new Random();

    /**
     * Simule un combat entre deux équipes de trois monstres.
     */
    public FightLog simulateTeamFight(List<MonsterDto> team1, List<MonsterDto> team2) {
        FightLog fightLog = new FightLog();
        fightLog.setTeam1(team1.stream().map(MonsterDto::getName).toList());
        fightLog.setTeam2(team2.stream().map(MonsterDto::getName).toList());
        fightLog.setFightDate(LocalDateTime.now());

        int turn = 0;
        while (!team1.isEmpty() && !team2.isEmpty() && turn < 50) {
            turn++;
            MonsterDto attacker = selectAttacker(team1, team2);
            MonsterDto defender = selectDefender(attacker, team1, team2);

            if (random.nextDouble() < 0.05) { // Esquive 5%
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
     * Récupère l'historique des combats avec pagination.
     */
    public List<FightLog> getPaginatedFightHistory(int page, int size) {
        return fightRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    /**
     * Récupère un combat spécifique par son ID.
     */
    public FightLog getFightById(String fightId) {
        return fightRepository.findById(fightId).orElse(null);
    }

    /**
     * Sélectionne un attaquant basé sur la vitesse.
     */
    private MonsterDto selectAttacker(List<MonsterDto> team1, List<MonsterDto> team2) {
        List<MonsterDto> allMonsters = new ArrayList<>(team1);
        allMonsters.addAll(team2);

        int maxSpeed = allMonsters.stream().mapToInt(MonsterDto::getSpeed).max().orElse(0);
        List<MonsterDto> fastestMonsters = allMonsters.stream().filter(m -> m.getSpeed() == maxSpeed).toList();

        return fastestMonsters.get(random.nextInt(fastestMonsters.size()));
    }

    /**
     * Sélectionne un défenseur dans l'équipe adverse.
     */
    private MonsterDto selectDefender(MonsterDto attacker, List<MonsterDto> team1, List<MonsterDto> team2) {
        List<MonsterDto> opponents = team1.contains(attacker) ? team2 : team1;
        return opponents.get(random.nextInt(opponents.size()));
    }

    /**
     * Calcule les dégâts en prenant en compte les résistances, faiblesses et coups critiques.
     */
    private int calculateDamage(MonsterDto attacker, MonsterDto defender) {
        int baseDamage = Math.max(0, attacker.getAtk() - defender.getDef());
        int typeModifier = getTypeModifier(attacker, defender);

        boolean isCritical = random.nextDouble() < 0.1; // Coup critique 10%
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
    private boolean useSkill(MonsterDto attacker, MonsterDto defender, FightLog fightLog, int turn) {
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
    private void removeDefeatedMonster(MonsterDto defeated, List<MonsterDto> team1, List<MonsterDto> team2) {
        Iterator<MonsterDto> iterator1 = team1.iterator();
        while (iterator1.hasNext()) {
            if (iterator1.next().equals(defeated)) {
                iterator1.remove();
                break;
            }
        }

        Iterator<MonsterDto> iterator2 = team2.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().equals(defeated)) {
                iterator2.remove();
                break;
            }
        }
    }
}
