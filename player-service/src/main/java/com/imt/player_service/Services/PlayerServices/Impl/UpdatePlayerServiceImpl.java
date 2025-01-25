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
                .level(player.getLevel() != 0 ? player.getLevel() : foundPlayer.getLevel())
                .experience(player.getExperience() != 0
                        ? player.getExperience()
                        : foundPlayer.getExperience())
                .build();

        playerRepository.save(updatedPlayer);

    }

}
