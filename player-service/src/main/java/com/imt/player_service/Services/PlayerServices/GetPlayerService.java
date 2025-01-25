package com.imt.player_service.Services.PlayerServices;


import com.imt.player_service.Model.Player;

public interface GetPlayerService {
     Player byUserName(String  username);

}
