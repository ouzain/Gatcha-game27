package com.imt.authentication_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthService.AuthenticationService;
import com.imt.authentication_service.AuthService.CrudServices.AddService;
import com.imt.authentication_service.AuthService.CrudServices.DeleteService;
import com.imt.authentication_service.AuthService.CrudServices.GetService;
import com.imt.authentication_service.AuthService.CrudServices.UpdateService;
import com.imt.authentication_service.Dto.AuthEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api-auth")
public class AuthController {
    @Autowired
    private  AuthenticationService authenticationService;
    @Autowired
    private AddService addService;
    @Autowired
    private GetService getService;
    @Autowired
    private UpdateService updateService;
    @Autowired
    private DeleteService deleteService;

    // l'authentification
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam (value = "username", required = true) String username,
                                        @RequestParam(value="password", required = true) String password) {
        String token = authenticationService.authenticate(username, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // validation du token
    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        String username = authenticationService.validateToken(token);
        if (username != null) {
            return ResponseEntity.ok(username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired or invalid");
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerPlayerCredentials(@RequestBody AuthEntityDto authEntityDto) {

        // 1) Vérifier que le DTO n'est pas null et possède des champs valides
        if (authEntityDto == null
                || authEntityDto.getUsername() == null
                || authEntityDto.getPassword() == null
                || authEntityDto.getUsername().isBlank()
                || authEntityDto.getPassword().isBlank()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("AuthEntityDto invalide (username/password manquants ou vides).");
        }

        // 2) Vérifier les contraintes de mot de passe (longueur min)
        if (authEntityDto.getPassword().length() < 4) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Le mot de passe doit comporter au moins 4 caractères.");
        }

        // 3) Vérifier si l'utilisateur existe déjà en base
        //    (ex : via un service "getByUsername(authEntityDto.getUsername())")
        AuthEntity existingUser = this.getService.byUserName(authEntityDto.getUsername());
        if (existingUser != null) {
            // L’utilisateur existe déjà donc Conflit
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Un utilisateur avec ce nom existe déjà.");
        }

        // 4) Convertir le DTO en entité
        AuthEntity player = authEntityDto.toAuthEntity();

        // 5) Appeler la couche service pour l'enregistrer
        this.addService.execute(player);

        // 6) Retourner un code 201 Created en cas de succès
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Player credentials registered.");
    }


    @GetMapping(value = "/get-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerCredentials(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "token", required = false) String token
    ) {

        // 1) Valider l'input : s'assurer qu'on a au moins un des deux paramètres
        if ((username == null || username.isBlank()) && (token == null || token.isBlank())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Veuillez fournir soit un username, soit un token (ou les deux).");
        }

        AuthEntity foundPlayer = null;

        // 2) Récupérer le user soit par username, soit par token (ou les deux)
        if (username != null && !username.isBlank()) {
            foundPlayer = this.getService.byUserName(username);
        } else if (token != null && !token.isBlank()) {
            foundPlayer = this.getService.byToken(token);
        }

        // 3) Gérer le cas où l’utilisateur n’existe pas
        if (foundPlayer == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Aucun utilisateur correspondant n’a été trouvé.");
        }

        // 4) Conversion en DTO et retour
        AuthEntityDto dto = foundPlayer.toAuthEntityDto();
        return ResponseEntity.ok(dto);
    }


    @PatchMapping(value = "/update",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> updatePlayer(@RequestBody AuthEntityDto playerDto){

        if (playerDto == null
                || playerDto.getUsername() == null
                || playerDto.getPassword() == null
                || playerDto.getUsername().isBlank()
                || playerDto.getPassword().isBlank()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("AuthEntityDto invalide (username/password manquants ou vides).");
        }
        AuthEntity updatedPlayer = playerDto.toAuthEntity();
        try {
            this.updateService.execute(updatedPlayer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la mise à jour du joueur.");
        }
        return null;
    }


    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePlayer(@RequestParam(value = "username", required = true) String username) {

        // 1) Vérifier que le paramètre "username" n'est pas null ou vide
        if (username == null || username.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Le paramètre 'username' est obligatoire et ne doit pas être vide.");
        }

        // 2) Vérifier si l'utilisateur existe avant de le supprimer
        AuthEntity existingUser = this.getService.byUserName(username);
        if (existingUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Aucun utilisateur trouvé pour le username : " + username);
        }

        // 3) Supprimer l’utilisateur
        this.deleteService.execute(username);

        // 4) Retourner un statut 204 NO_CONTENT en cas de succès
        return ResponseEntity.noContent().build();
    }

}


