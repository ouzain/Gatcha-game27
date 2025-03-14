package com.imt.player_service.Services.PlayerServices.Impl;


import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.PlayerServices.AbstractPlayerService;
import com.imt.player_service.Services.PlayerServices.UpdatePlayerService;
import org.springframework.stereotype.Service;

@Service(value = "UpdateUserService")
public class UpdatePlayerServiceImpl extends AbstractPlayerService implements UpdatePlayerService {

    private String getUsernameFromToken(String token) {
        // le token est sous forme 'username-date-time'
        String[] parts = token.split("-");
        if (parts.length >= 1) {
            return parts[0]; // la première partie est le username
        }
        return null; // si le format du token n'est pas valide
    }
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
        String playerUsername = getUsernameFromToken(token);
        Player foundPlayer = playerRepository.findByUsername(playerUsername);
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

    // Méthode pour calculer maxMonsters en fonction du niveau
    @Override
    public int calculateMaxMonsters(int level) {
        // logique pour ajuster maxMonsters selon le level
        if (level == 2) {
            return 5;  // Niveau 1, 3 monstres
        } else if (level == 3) {
            return 7;  // Niveau 2, 5 monstres
        } else if (level == 1) {
            return 3;

        } else {
            return 5 + (level - 2) * 2;  // Plus de monstres pour les niveaux supérieurs
        }
    }

}
