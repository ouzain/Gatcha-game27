package com.imt.player_service.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.imt.player_service.Dto.PlayerDto;
import com.imt.player_service.OpenFeing.AuthServiceClient;
import com.imt.player_service.Services.PlayerServices.AddPlayerService;
import com.imt.player_service.Services.PlayerServices.DeletePlayerService;
import com.imt.player_service.Services.PlayerServices.GetPlayerService;
import com.imt.player_service.Services.PlayerServices.UpdatePlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.imt.player_service.Model.Player;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private AddPlayerService addPlayerService;
    @Autowired
    private GetPlayerService getPlayerService;
    @Autowired
    private DeletePlayerService deletePlayerService;
    @Autowired
    private UpdatePlayerService updatePlayerService;
    @Autowired
    AuthServiceClient authServiceClient;


    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public  void addPlayer(@RequestBody PlayerDto playerDto)  {

        //TODO : Verifier sur userDto n'est pas null ou invalid
        Player player = playerDto.toPlayerEntity();

        this.addPlayerService.execute(player);

        // Envoyer les données d'authentification à l'api d'authentification
        AuthServiceClient.AuthRequest authRequest = new AuthServiceClient.AuthRequest();
        authRequest.setUsername(playerDto.getUsername());
        authRequest.setPassword(playerDto.getPassword());
        authServiceClient.registerPlayerCredentials(authRequest);
    }

    @GetMapping(value = "/get-user", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public PlayerDto getPlayer(@RequestParam(value = "username", required = true) String username){

        Player foundPlayer = this.getPlayerService.byUserName(username);

        //TODO : checker si le user trouvé n'est pas null
        return foundPlayer.toPlayerDtoEntity();

        //TODO : ajouter un requestParam token afin de pouvoir récupérer un user par son token


    }

    @PatchMapping(value = "/update",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updatePlayer(@RequestBody PlayerDto playerDto){
        //TODO: checker userDto n'est pas null
        Player updatedPlayer = playerDto.toPlayerEntity();
        this.updatePlayerService.execute(updatedPlayer);
    }


    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/delete")
    public void deletePlayer(@RequestParam(value= "username", required = true) String username) {
         //TODO: verifier si l'objet n'est pas null
        this.deletePlayerService.execute(username);
    }


}
