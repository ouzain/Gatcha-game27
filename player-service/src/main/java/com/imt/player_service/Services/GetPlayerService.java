package com.imt.player_service.Services;


import com.imt.player_service.Model.Player;

public interface GetPlayerService {
     Player byUserName(String  username);
     Player byToken(String token);

}
