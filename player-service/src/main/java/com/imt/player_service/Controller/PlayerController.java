package com.imt.player_service.Controller;

import com.imt.player_service.Dto.PlayerDto;
import com.imt.player_service.OpenFeing.AuthServiceClient;
import com.imt.player_service.OpenFeing.InvokClient;
import com.imt.player_service.Services.PlayerServices.AddPlayerService;
import com.imt.player_service.Services.PlayerServices.DeletePlayerService;
import com.imt.player_service.Services.PlayerServices.GetPlayerService;
import com.imt.player_service.Services.PlayerServices.UpdatePlayerService;
import com.imt.player_service.Model.Player;
import com.imt.player_service.Model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public ResponseEntity<ApiResponse> addPlayer(@RequestBody PlayerDto playerDto) {
        if (playerDto == null || playerDto.getUsername() == null || playerDto.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Le DTO du player est invalide (username/password manquant).", false));
        }

        // Envoi des données à l’API d’authentification pour création
        AuthServiceClient.AuthRequest authRequest = new AuthServiceClient.AuthRequest();
        authRequest.setUsername(playerDto.getUsername());
        authRequest.setPassword(playerDto.getPassword());

        try {
            // Appel du client pour l'enregistrement
            ResponseEntity<String> registerResponse = authServiceClient.registerPlayerCredentials(authRequest);

            if (!registerResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(registerResponse.getStatusCode())
                        .body(new ApiResponse("Erreur de création d’utilisateur : " + registerResponse.getBody(), false));
            }

            // Authentification et récupération du token
            ResponseEntity<String> loginResponse = authServiceClient.login(authRequest);

            if (!loginResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(loginResponse.getStatusCode())
                        .body(new ApiResponse("Échec de l’authentification : " + loginResponse.getBody(), false));
            }

            String token = loginResponse.getBody();
            if (token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Impossible de récupérer le token d’authentification.", false));
            }

            // Création et enregistrement du joueur
            Player player = Player.Builder.builder()
                    .username(playerDto.getUsername())
                    .token(token)
                    .level(1)
                    .experience(50)
                    .monsterList(playerDto.getMonsterList() != null ? playerDto.getMonsterList() : new ArrayList<Integer>())
                    .build();

            addPlayerService.execute(player);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Le joueur " + playerDto.getUsername() + " a été créé et authentifié avec succès.", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de l'appel à l'API d'authentification : " + e.getMessage(), false));
        }
    }

    @GetMapping(value = "/get-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getPlayer(@RequestParam(value = "username", required = true) String username) {
        Player foundPlayer = this.getPlayerService.byUserName(username);

        if (foundPlayer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Aucun joueur trouvé pour le nom d'utilisateur : " + username, false));
        }

        return ResponseEntity.ok(new ApiResponse("Joueur trouvé avec succès", true, foundPlayer.toPlayerDtoEntity()));
    }

    @PatchMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> updatePlayer(@RequestBody PlayerDto playerDto) {
        if (playerDto == null || playerDto.getUsername() == null || playerDto.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Le DTO du player est invalide (username/password manquants).", false));
        }

        Player updatedPlayer = playerDto.toPlayerEntity();
        try {
            this.updatePlayerService.execute(updatedPlayer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de la mise à jour du joueur : " + e.getMessage(), false));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("Le joueur a été mis à jour avec succès.", true));
    }


    @DeleteMapping(value = "/delete")
    public ResponseEntity<ApiResponse> deletePlayer(@RequestParam(value = "username", required = true) String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Le paramètre 'username' est obligatoire et ne doit pas être vide.", false));
        }

        Player existingUser = this.getPlayerService.byUserName(username);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Aucun joueur trouvé pour le nom d'utilisateur : " + username, false));
        }

        this.deletePlayerService.execute(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("Le joueur a été supprimé avec succès.", true));
    }

    @PostMapping(value = "/acquire-monster")
    public ResponseEntity<ApiResponse> acquireMonster(@RequestHeader("Authorization") String token,
                                                      @RequestParam String playerId) {
        try {
            ResponseEntity<Integer> response = invokClient.invokeMonster(token, playerId);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(response.getStatusCode())
                        .body(new ApiResponse("Échec de l'acquisition du monstre : " + response.getBody(), false));
            }

            Integer monsterId = response.getBody();
            if (monsterId == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Erreur : l'ID du monstre est introuvable.", false));
            }

            Player client = getPlayerService.byToken(token);
            client.addMonster(monsterId);

            return ResponseEntity.ok(new ApiResponse("Monstre acquis avec succès ! (ID: " + monsterId + ")", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de l'acquisition du monstre : " + e.getMessage(), false));
        }
    }

}
