package com.imt.player_service.Services.Impl;


import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.AbstractPlayerService;
import com.imt.player_service.Services.AddPlayerService;
import org.springframework.stereotype.Service;

@Service(value = "AddUserService")
public class AddPlayerServiceImpl extends AbstractPlayerService implements AddPlayerService {

    @Override
    public void execute(Player player){
        this.playerRepository.save(player);
    }
}
