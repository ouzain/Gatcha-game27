package com.imt.player_service.Services.PlayerServices;
import com.imt.player_service.Model.Player;

public interface UpdatePlayerService {
    void execute(Player player);
    boolean addExp(String token, int exp);
}
