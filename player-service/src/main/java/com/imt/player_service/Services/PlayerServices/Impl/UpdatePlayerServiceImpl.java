package com.imt.player_service.Services.PlayerServices.Impl;


import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.PlayerServices.AbstractPlayerService;
import com.imt.player_service.Services.PlayerServices.UpdatePlayerService;
import org.springframework.stereotype.Service;

@Service(value = "UpdateUserService")
public class UpdatePlayerServiceImpl extends AbstractPlayerService implements UpdatePlayerService {

    @Override
    public void execute(Player player) {
        Player foundPlayer = playerRepository.findByUsername(player.getUsername());
        //TODO: ajouter un attribut uuid pour pouvoir rechercher un player par son uuid, car on peut bien vouloir modifier le username
        if (foundPlayer == null) {
            throw new RuntimeException("User not found: " + player.getUsername());
        }

        Player updatedPlayer = Player.Builder.builder()
                .id(foundPlayer.getId())
                .username(player.getUsername() != null ? player.getUsername() : foundPlayer.getUsername())
                .token(player.getToken() !=null ? player.getToken() : foundPlayer.getToken())
                .level(player.getLevel() != 0 ? player.getLevel() : foundPlayer.getLevel())
                .experience(player.getExperience() != 0
                        ? player.getExperience()
                        : foundPlayer.getExperience())
                .monsterList(player.getMonsterList() != null && !player.getMonsterList().isEmpty()
                        ? player.getMonsterList()
                        : foundPlayer.getMonsterList())
                .maxExperience(player.getMaxExperience() != 0
                        ? player.getMaxExperience()
                        : foundPlayer.getMaxExperience())
                .maxMonsters(player.getMaxMonsters() != 0
                        ? player.getMaxMonsters()
                        : foundPlayer.getMaxMonsters())
                .build();

        playerRepository.save(updatedPlayer);

    }

    @Override
    public boolean addExp(String token, int exp) {
        Player foundPlayer = playerRepository.findByToken(token);
        double experienceLevel = 50;
        if (foundPlayer == null) {
            throw new RuntimeException("User not found: " + token);
        }

        double newExp = foundPlayer.getExperience() + exp;
        int requiredExp = (int) (experienceLevel * 1.1);
        boolean levelUp = false;

        if (newExp >= requiredExp) {
            Player updatedPlayer = Player.Builder.builder()
                    .id(foundPlayer.getId())
                    .username(foundPlayer.getUsername())
                    .token(foundPlayer.getToken())
                    .level(foundPlayer.getLevel() + 1)
                    .experience(requiredExp)
                    .monsterList(foundPlayer.getMonsterList())
                    .maxExperience(foundPlayer.getMaxExperience())
                    .maxMonsters(foundPlayer.getMaxMonsters() + 1)
                    .build();
            playerRepository.save(updatedPlayer);
            levelUp = true;
            experienceLevel = requiredExp;
        } else {
            Player updatedPlayer = Player.Builder.builder()
                    .id(foundPlayer.getId())
                    .username(foundPlayer.getUsername())
                    .token(foundPlayer.getToken())
                    .level(foundPlayer.getLevel())
                    .experience((int) newExp)
                    .monsterList(foundPlayer.getMonsterList())
                    .maxExperience(foundPlayer.getMaxExperience())
                    .maxMonsters(foundPlayer.getMaxMonsters())
                    .build();
            playerRepository.save(updatedPlayer);
        }


        return levelUp;
    }

}
