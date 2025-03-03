package com.imt.player_service.Services;
import com.imt.player_service.Model.Player;
import org.bson.types.ObjectId;

public interface UpdatePlayerService {
    void execute(Player player);
    void levelUp(Player player);
}
