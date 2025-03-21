package com.imt.player_service.OpenFeing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {
    @GetMapping("/api-auth/validate")
    ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);

    @GetMapping("/api-auth/login")
    ResponseEntity<String> login(@RequestParam(value = "username", required = true) String username,
                                 @RequestParam(value = "password", required = true) String password);


    @PostMapping(value = "/api-auth/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<String> registerPlayerCredentials(@RequestBody AuthRequest authEntityDto);
    class AuthRequest {

        @NotBlank
        private String username;


        @NotBlank
        @Size(min = 4, message = "Le mot de passe doit comporter au moins 6 caractères.")
        private String password;

        public AuthRequest() {
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
}
