package com.imt.player_service.Services.PlayerServices.Impl;
import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.PlayerServices.AbstractPlayerService;
import com.imt.player_service.Services.PlayerServices.GetPlayerService;
import org.springframework.stereotype.Service;

@Service(value = "GetUserService")
public class GetPlayerServiceImpl extends AbstractPlayerService implements GetPlayerService {

    @Override
    public Player byUserName(String username){

        //TODO : Faire le check au cas où le User serait vide ie Null
        return this.playerRepository.findByUsername(username);
    }

}
