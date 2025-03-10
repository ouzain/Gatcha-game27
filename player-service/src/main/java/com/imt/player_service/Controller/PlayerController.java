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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        // Envoi des données à l’API d’authentification pour sauvegarde des credentials
        AuthServiceClient.AuthRequest authRequest = new AuthServiceClient.AuthRequest();
        authRequest.setUsername(playerDto.getUsername());
        authRequest.setPassword(playerDto.getPassword());

        try {
            // Appel à l'api authentif pour l'enregistrement
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

            // Parser la reponse json pour extraire le token
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(loginResponse.getBody());

                // extraire le token du champ  "data"
                String token = jsonResponse.get("data").asText();

                if (token.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse("Impossible de récupérer le token d’authentification.", false));
                }

                // Définir maxMonsters en fonction du niveau du joueur
                int maxMonsters = calculateMaxMonsters(playerDto.getLevel());

                // Création et enregistrement du player
                Player player = Player.Builder.builder()
                        .username(playerDto.getUsername())
                        .token(token)
                        .level(playerDto.getLevel() == 0 ? 1 : playerDto.getLevel())
                        .experience(playerDto.getExperience() == 0 ? 50 : playerDto.getExperience())
                        .monsterList(playerDto.getMonsterList() != null ? playerDto.getMonsterList() : new ArrayList<Integer>())
                        .maxExperience(100)
                        .maxMonsters(maxMonsters)  // assignation dynamique de maxMonsters
                        .build();

                addPlayerService.execute(player);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiResponse("Le joueur " + playerDto.getUsername() + " a été créé et authentifié avec succès.", true));

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Erreur lors de la récupération du token : " + e.getMessage(), false));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de l'appel à l'API d'authentification : " + e.getMessage(), false));
        }
    }

    // Méthode pour calculer maxMonsters en fonction du niveau
    private int calculateMaxMonsters(int level) {
        // logique pour ajuster maxMonsters selon le level
        if (level == 1) {
            return 3;  // Niveau 1, 3 monstres
        } else if (level == 2) {
            return 5;  // Niveau 2, 5 monstres
        } else {
            return 5 + (level - 2) * 2;  // Plus de monstres pour les niveaux supérieurs
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
    public ResponseEntity<ApiResponse> acquireMonster(@RequestParam("Authorization") String token) {
        try {
            ResponseEntity<String> response = invokClient.invokeMonster(token);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(response.getStatusCode())
                        .body(new ApiResponse("Échec de l'acquisition du monstre : " + response.getBody(), false));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            // extraire le token du champ  "data"
            Integer monsterId = jsonResponse.get("data").asInt();

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

    @PostMapping(value = "/add-exp-user")
    public ResponseEntity<?> addExpUser(@RequestParam("Authorization") String token, @RequestParam(value = "exp", required = true) int exp) {
        Player client = getPlayerService.byToken(token);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Aucun joueur trouvé pour le token d'authentification fourni.", false));
        }
        updatePlayerService.addExp(token, exp);

        return ResponseEntity.ok(new ApiResponse("Expérience ajoutée avec succès !", true));
    }

}
