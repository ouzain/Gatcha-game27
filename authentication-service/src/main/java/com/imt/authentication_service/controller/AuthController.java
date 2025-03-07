package com.imt.authentication_service.controller;

import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthModel.ApiResponse;
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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api-auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AddService addService;
    @Autowired
    private GetService getService;
    @Autowired
    private UpdateService updateService;
    @Autowired
    private DeleteService deleteService;

    // l'authentification
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthEntityDto authEntityDto) {
        String token = authenticationService.authenticate(authEntityDto.getUsername(), authEntityDto.getPassword());

        if (token != null) {
            return ResponseEntity.ok(new ApiResponse("Authentication successful", true, token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid username or password", false));
        }
    }

    // validation du token
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Missing or invalid token", false));
        }

        String token = authorizationHeader.substring(7);
        String username = authenticationService.validateToken(token);
        if (username != null) {
            return ResponseEntity.ok(new ApiResponse("Token is valid", true, username));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Token expired or invalid", false));
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> registerPlayerCredentials(@RequestBody AuthEntityDto authEntityDto) {
        if (authEntityDto == null || authEntityDto.getUsername() == null || authEntityDto.getPassword() == null ||
                authEntityDto.getUsername().isBlank() || authEntityDto.getPassword().isBlank()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("AuthEntityDto invalid (missing or empty username/password).", false));
        }

        if (authEntityDto.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Password must be at least 4 characters long.", false));
        }

        AuthEntity existingUser = this.getService.byUserName(authEntityDto.getUsername());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("A user with this name already exists.", false));
        }

        AuthEntity player = authEntityDto.toAuthEntity();
        this.addService.execute(player);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Player credentials registered.", true));
    }

    @GetMapping(value = "/get-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getPlayerCredentials(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "token", required = false) String token) {

        if ((username == null || username.isBlank()) && (token == null || token.isBlank())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Please provide either a username or a token (or both).", false));
        }

        AuthEntity foundPlayer = null;
        if (username != null && !username.isBlank()) {
            foundPlayer = this.getService.byUserName(username);
        } else if (token != null && !token.isBlank()) {
            foundPlayer = this.getService.byToken(token);
        }

        if (foundPlayer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No user found matching the provided credentials.", false));
        }

        AuthEntityDto dto = foundPlayer.toAuthEntityDto();
        return ResponseEntity.ok(new ApiResponse("User found", true, dto));
    }

    @PatchMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> updatePlayer(@RequestBody AuthEntityDto playerDto) {
        if (playerDto == null || playerDto.getUsername() == null || playerDto.getPassword() == null ||
                playerDto.getUsername().isBlank() || playerDto.getPassword().isBlank()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("AuthEntityDto invalid (missing or empty username/password).", false));
        }

        AuthEntity updatedPlayer = playerDto.toAuthEntity();
        try {
            this.updateService.execute(updatedPlayer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error occurred while updating the player.", false));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Player updated successfully.", true));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ApiResponse> deletePlayer(@RequestParam(value = "username", required = true) String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("The 'username' parameter is required and cannot be empty.", false));
        }

        AuthEntity existingUser = this.getService.byUserName(username);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No user found for the username: " + username, false));
        }

        this.deleteService.execute(username);
        return ResponseEntity.noContent().build();
    }
}
