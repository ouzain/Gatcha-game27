package com.imt.player_service.Services.Impl;


import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.AbstractPlayerService;
import com.imt.player_service.Services.DeletePlayerService;
import org.springframework.stereotype.Service;

@Service(value = "DeleteUserService")
public class DeletePlayerServiceImpl extends AbstractPlayerService implements DeletePlayerService {

    @Override
    public void execute(String username){
        Player player = playerRepository.findByUsername(username);
        //TODO: Verifier si le user n'est pas null
        playerRepository.delete(player);
    }
}
