package com.imt.monster_service.api;

import com.imt.monster_service.Dto.MonsterDto;
import com.imt.monster_service.Model.ApiResponse;
import com.imt.monster_service.Model.ExperienceRequest;
import com.imt.monster_service.Model.Monster;
import com.imt.monster_service.crudServices.MonsterCrudService;
import com.imt.monster_service.Model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monsters")
public class MonsterController {

    @Autowired
    private MonsterCrudService monsterCrudService;

    // Récupérer tous les monstres
    @GetMapping("/get-all")
    public ResponseEntity<List<Monster>> getAllMonsters() {
        List<Monster> monsters = monsterCrudService.getAllMonsters();
        return ResponseEntity.ok(monsters);
    }

    // Récupérer un monstre par ID
    @GetMapping("/{id}")
    public ResponseEntity<Monster> getMonsterById(@PathVariable int id) {
        Optional<Monster> monster = monsterCrudService.getMonsterById(id);
        if (monster.isPresent()) {
            return ResponseEntity.ok(monster.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // ajoutr des monstre
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Integer> addMonsters(@RequestBody List<MonsterDto> monsterDtos) {
        // Convertir les DTOs en entités
        List<Monster> monsters = monsterDtos.stream()
                .map(MonsterDto::toMonsterEntity)
                .collect(Collectors.toList());

        List<Monster> savedMonsters = monsterCrudService.addMonsters(monsters);

        // retourner les IDs des monstres ajoutés
        return savedMonsters.stream()
                .map(Monster::getId)
                .collect(Collectors.toList());
    }

    // Supprimer un monstre par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable int id) {
        monsterCrudService.deleteMonster(id);
        return ResponseEntity.noContent().build();
    }


    // Gagner de l'expérience pour un monstre
    @PostMapping("/{id}/experience")
    public ResponseEntity<Monster> gainExperience(@PathVariable int id, @RequestBody ExperienceRequest experienceRequest) {
        Optional<Monster> monsterOpt = monsterCrudService.getMonsterById(id);
        if (monsterOpt.isPresent()) {
            Monster monster = monsterOpt.get();
            monster.setExperience(monster.getExperience() + experienceRequest.getAmount());
            monsterCrudService.saveMonster(monster);  // Sauvegarde de l'état mis à jour
            return ResponseEntity.ok(monster);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Monter de niveau pour un monstre
    @PostMapping("/{id}/level-up")
    public ResponseEntity<Monster> levelUp(@PathVariable int id) {
        Optional<Monster> monsterOpt = monsterCrudService.getMonsterById(id);
        if (monsterOpt.isPresent()) {
            Monster monster = monsterOpt.get();
            if (monster.getExperience() < monster.getMaxExperience()) {
                monster.setExperience(monster.getExperience() + 1);
                monsterCrudService.saveMonster(monster);
                return ResponseEntity.ok(monster);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Niveau max atteint
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // améliorer une compétence on augmente le dmg
    @PostMapping("/{monsterId}/skills/{skillId}/upgrade")
    public ResponseEntity<Skill> upgradeSkill(@PathVariable int monsterId, @PathVariable int skillId) {
        Optional<Monster> monsterOpt = monsterCrudService.getMonsterById(monsterId);
        if (monsterOpt.isPresent()) {
            Monster monster = monsterOpt.get();
            Optional<Skill> skillOpt = monster.getSkills().stream().filter(skill -> skill.getNum() == skillId).findFirst();
            if (skillOpt.isPresent()) {
                Skill skill = skillOpt.get();
                if (skill.getDmg() < 100* skill.getLvlMax()) {
                    skill.setDmg(skill.getDmg() + 45); // améliorer le dmg
                    monsterCrudService.saveMonster(monster);
                    return ResponseEntity.ok(skill);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // Niveau max atteint
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/summon")
    public ResponseEntity<ApiResponse> generateRandomMonster() {
        try {
            Monster randomMonster = monsterCrudService.summonRandomMonster();
            MonsterDto monsterDto = randomMonster.toMonsterDto();
            return ResponseEntity.ok(new ApiResponse("Monstre invoqué avec succès", true, monsterDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("pas de monstre dans la bdd",false));
        }
    }

}
