package com.imt.player_service.Services.Impl;


import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.AbstractPlayerService;
import com.imt.player_service.Services.UpdatePlayerService;
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
                .build();

        playerRepository.save(updatedPlayer);

    }

    //TODO: Gestion du level up selon les combats gagnés soit selon le niveau il faut gagner
    // x combat pour augmenter et il faut invoquer un monstre lors dulevel up
    @Override
    public void levelUp(Player player) {
        Player foundPlayer = playerRepository.findByUsername(player.getUsername());

        if (foundPlayer == null) {
            throw new RuntimeException("User not found: " + player.getUsername());
        }

        // Mise à jour du niveau et de l'expérience
        foundPlayer.setLevel(foundPlayer.getLevel() + 1);
        foundPlayer.setExperience(0);  // Reset de l'XP après un level up

        // Sauvegarde dans MongoDB
        playerRepository.save(foundPlayer);
    }


}
