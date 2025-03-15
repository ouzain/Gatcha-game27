package com.imt.fight_service.services;

import com.imt.fight_service.dao.BattleRepository;
import com.imt.fight_service.model.Battle;
import com.imt.fight_service.model.BattleAction;
import com.imt.fight_service.model.FightingMonster;
import com.imt.fight_service.model.Skill;
import com.imt.fight_service.openfeign.MonsterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class CombatService {

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private MonsterClient monsterClient;

    // Logique pour démarrer un combat entre deux monstres
    public Battle startCombat(FightingMonster monster1, FightingMonster monster2) {
        // Récupérer les informations des deux monstres
        Integer monster1Id = monster1.getId();
        Integer monster2Id = monster2.getId();


        // Simuler le combat
        List<BattleAction> actions = simulateCombat(monster1, monster2);

        // Déterminer le gagnant
        Integer winnerId = actions.get(actions.size() - 1).getDamage() > 0 ? monster1Id : monster2Id;

        // Créer un objet Battle avec les résultats
        Battle battle = new Battle.Builder()
                .withMonster1Id(monster1Id)
                .withMonster2Id(monster2Id)
                .withActions(actions)
                .withWinnerId(winnerId)
                .withDate(java.time.LocalDate.now().toString())
                .build();

        return battleRepository.save(battle);
    }

    // Simule le combat entre les deux monstres et retourne une liste d'actions
    private List<BattleAction> simulateCombat(FightingMonster monster1, FightingMonster monster2) {
        List<BattleAction> actions = new ArrayList<>();
        int turn = 1;

        // On détermine l'ordre des attaques basé sur la vitesse
        boolean monster1AttacksFirst = monster1.getVit() > monster2.getVit();

        // Début du combat avec les deux monstres
        while (monster1.getHp() > 0 && monster2.getHp() > 0) {
            if (monster1AttacksFirst) {
                actions.add(executeAttack(turn++, monster1, monster2));
                if (monster2.getHp() <= 0) break; // Si le monstre 2 est vaincu, arrêter le combat
                actions.add(executeAttack(turn++, monster2, monster1));
            } else {
                actions.add(executeAttack(turn++, monster2, monster1));
                if (monster1.getHp() <= 0) break; // Si le monstre 1 est vaincu, arrêter le combat
                actions.add(executeAttack(turn++, monster1, monster2));
            }
        }

        return actions;
    }

    // exécuter une attaque d'un monstre contre l'autre
    private BattleAction executeAttack(int turn, FightingMonster attacker, FightingMonster defender) {
        // Calculer les dégâts infligés
        int damage = calculateDamage(attacker, defender);

        // Appliquer les dégâts
        defender.setHp(defender.getHp() - damage);

        // Créer une action de bataille
        return new BattleAction(turn, attacker.getId(), "attack", damage, defender.getHp());
    }

    // Calcul des dégâts en fonction des éléments, de l'attaque, de la défense et des compétences
    private int calculateDamage(FightingMonster attacker, FightingMonster defender) {
        // Calcul de base en fonction de l'attaque et de la défense
        int baseDamage = attacker.getAtk() - defender.getDef();

        // Appliquer un modificateur d'élément (par ex: Feu > Eau)
        double elementMultiplier = calculateElementMultiplier(attacker, defender);
        baseDamage *= elementMultiplier;

        // Appliquer les dégâts des compétences
        int skillDamage = 0;
        for (Skill skill : attacker.getSkills()) {
            if (skill.getCooldown() == 0) { // Si la compétence est prête
                skillDamage += skill.getDmg();
                // Appliquer le ratio de la compétence
                skillDamage += (int) (attacker.getAtk() * (skill.getRatio().getPercent() / 100));
            }
        }

        // Calcul final des dégâts
        int totalDamage = baseDamage + skillDamage;
        return Math.max(totalDamage, 0); // Empêcher les dégâts négatifs
    }

    // Calcul des modificateurs d'élément (par exemple, Feu contre Eau)
    private double calculateElementMultiplier(FightingMonster attacker, FightingMonster defender) {
        // Exemple de logique pour les éléments (à ajuster selon votre logique)
        if (attacker.getElement() == FightingMonster.ElementType.FIRE && defender.getElement() == FightingMonster.ElementType.WATER) {
            return 1.5; // Le feu est plus fort contre l'eau
        } else if (attacker.getElement() == FightingMonster.ElementType.WATER && defender.getElement() == FightingMonster.ElementType.FIRE) {
            return 0.5; // L'eau est moins efficace contre le feu
        } else if (attacker.getElement() == defender.getElement()) {
            return 1.2; // Les mêmes éléments s'auto-boostent
        } else {
            return 1.0; // Aucun avantage particulier
        }
    }

    // Récupérer un combat par son ID
    public Battle getBattleById(String battleId) {
        return battleRepository.findById(battleId).orElseThrow(() -> new RuntimeException("Battle not found"));
    }

    // Récupérer l'historique de tous les combats
    public List<Battle> getAllBattles() {
        return battleRepository.findAll();
    }
}
