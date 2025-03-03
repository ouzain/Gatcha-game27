package com.imt.player_service.Services.Impl;
import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.AbstractPlayerService;
import com.imt.player_service.Services.GetPlayerService;
import org.springframework.stereotype.Service;

@Service(value = "GetUserService")
public class GetPlayerServiceImpl extends AbstractPlayerService implements GetPlayerService {

    @Override
    public Player byUserName(String username){

        //TODO : Faire le check au cas où le User serait vide ie Null
        return this.playerRepository.findByUsername(username);
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Player byToken(String token) {
        //TODO : Faire le check au cas où le User serait vide ie Null
        return this.playerRepository.findByToken(token);
    }

}
