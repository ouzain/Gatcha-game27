package com.imt.player_service.Services.PlayerServices.Impl;
import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.PlayerServices.AbstractPlayerService;
import com.imt.player_service.Services.PlayerServices.GetPlayerService;
import org.springframework.stereotype.Service;
@Service(value = "GetUserService")
public class GetPlayerServiceImpl extends AbstractPlayerService implements GetPlayerService {

    @Override
    public Player byUserName(String username) {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        Player player = this.playerRepository.findByUsername(username);
        if (player == null) {
            throw new IllegalArgumentException("Player not found with username: " + username);
        }

        return player;
    }

    @Override
    public Player byToken(String token) {

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        Player player = this.playerRepository.findByToken(token);
        if (player == null) {
            throw new IllegalArgumentException("Player not found with token: " + token);
        }

        return player;
    }
}
