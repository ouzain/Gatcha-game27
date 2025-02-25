package com.imt.monster_service.api;

import com.imt.monster_service.Dto.MonsterDto;
import com.imt.monster_service.Model.Monster;
import com.imt.monster_service.crudServices.MonsterService;
import com.imt.monster_service.crudServices.UpdateMonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monsters")
public class MonsterController {

    @Autowired
    private MonsterService monsterService;
    @Autowired
    private UpdateMonsterService updateMonsterService;

    @GetMapping("/all")
    public List<Monster> getAllMonsters() {
        return monsterService.getAllMonsters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monster> getMonsterById(@PathVariable int id) {
        Optional<Monster> monster = monsterService.getMonsterById(id);
        return monster.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addMonster(@RequestBody MonsterDto monsterDto) {
        // Convertir le DTO en entité et ajouter le monstre
        Monster monster = monsterDto.toMonsterEntity();
        monsterService.addMonster(monster);

        // Retourner l'ID du monstre
        return monster.getId();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable int id) {
        monsterService.deleteMonster(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public void updateMonster(@RequestBody MonsterDto monsterDto){
        Monster monster = monsterDto.toMonsterEntity();
        updateMonsterService.execute(monster);
        
    }
}
