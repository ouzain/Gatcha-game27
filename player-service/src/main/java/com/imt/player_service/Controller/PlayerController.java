package com.imt.player_service.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.imt.player_service.Dto.PlayerDto;
import com.imt.player_service.OpenFeing.AuthServiceClient;
import com.imt.player_service.OpenFeing.InvokClient;
import com.imt.player_service.Services.PlayerServices.AddPlayerService;
import com.imt.player_service.Services.PlayerServices.DeletePlayerService;
import com.imt.player_service.Services.PlayerServices.GetPlayerService;
import com.imt.player_service.Services.PlayerServices.UpdatePlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    InvokClient invokClient;


    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addPlayer(@RequestBody PlayerDto playerDto) {

        // 1) Vérifications basiques sur playerDto
        if (playerDto == null || playerDto.getUsername() == null || playerDto.getPassword() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Le DTO du player est invalide (username/password manquant).");
        }

        // 2) Envoi des données à l’API d’authentification pour création
        AuthServiceClient.AuthRequest authRequest = new AuthServiceClient.AuthRequest();
        authRequest.setUsername(playerDto.getUsername());
        authRequest.setPassword(playerDto.getPassword());

        // Appel du client
        ResponseEntity<String> registerResponse = authServiceClient.registerPlayerCredentials(authRequest);

        // 3) Vérification de la réponse
        if (!registerResponse.getStatusCode().is2xxSuccessful()) {
            // par exemple, si l’API d’authentification renvoie 409 (Conflict) parce que l’utilisateur existe déjà
            return ResponseEntity
                    .status(registerResponse.getStatusCode())
                    .body("Erreur de création d’utilisateur : " + registerResponse.getBody());
        }

        // 4) Authentifier le player pour récupérer son token
        ResponseEntity<String> loginResponse = authServiceClient.login(playerDto.getUsername(), playerDto.getPassword());

        if (!loginResponse.getStatusCode().is2xxSuccessful()) {
            // L’authentification a échoué : mot de passe incorrect, user introuvable, etc.
            return ResponseEntity
                    .status(loginResponse.getStatusCode())
                    .body("Échec de l’authentification : " + loginResponse.getBody());
        }

        // 5) Récupération du token
        String token = loginResponse.getBody();
        if (token == null || token.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Impossible de récupérer le token d’authentification.");
        }

        // 6) Création de l’entité Player
        Player player = Player.Builder.builder()
                .username(playerDto.getUsername())
                .token(token)
                .level(playerDto.getLevel())
                .experience(playerDto.getExperience())
                .build();

        // 7) Enregistrer le player dans la bdd
        addPlayerService.execute(player);

        // 8) Tout s’est bien passé, retournez un statut 201 (ou 200) avec un message de succès
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Le joueur " + playerDto.getUsername() + " a été créé et authentifié avec succès.");
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

    @PostMapping(value = "/acquire-monster")
    public ResponseEntity<?> acquireMonster(@RequestHeader("Authorization") String token,
                                            @RequestParam String playerId) {

        // Appel vers l'API invocation, qui elle-même vérifie la validité du token auprès de l'API d'auth
        ResponseEntity<Integer> response = invokClient.invokeMonster(token, playerId);

        // vérifier si l'API invocation a bien répondu avec un code 2xx
        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body("Échec de l'acquisition du monstre : " + response.getBody());
        }

        // si on arrive ici, on est en succès (2xx)
        Integer monsterId = response.getBody();
        if (monsterId == null) {
            // Cas particulier : le corps de la réponse est vide
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur : l'ID du monstre est introuvable.");
        }

        // Récupérer le joueur et ajouter le monstre
        Player client = getPlayerService.byToken(token);
        client.addMonster(monsterId);

        // Réponse finale si tout est OK
        return ResponseEntity.ok("Monstre acquis avec succès ! (ID: " + monsterId + ")");
    }



}
