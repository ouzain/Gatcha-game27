
package com.imt.authentication_service.AuthService.Impl;


import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthService.AuthenticationService;
import com.imt.authentication_service.Dao.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service(value = "AuthenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthRepository authRepository;
    @Override
    public String authenticate(String username, String password) {
        AuthEntity authEntity = authRepository.findByUsername(username);
        if (authEntity != null && authEntity.getPassword().equals(password)) {

            String token = generateToken(username);
            authEntity.setToken(token);
            authEntity.setTokenExpiration(calculateTokenExpiration());
            authRepository.save(authEntity);

            return token;
        } else {
            return null;
        }
    }

    private String generateToken(String username) {
        //  token avec le format  :username-date-time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return username + "-" + formattedDateTime;
    }


    private LocalDateTime calculateTokenExpiration() {
        // calcul date d'expiration du token : une heure à partir de maintenant
        return LocalDateTime.now().plusHours(1);
    }

     private boolean isTokenExpired(LocalDateTime expiration) {
        return LocalDateTime.now().isAfter(expiration);
    }
    @Override
    public String validateToken(String token) {
        AuthEntity authEntity = authRepository.findByToken(token);
        if (authEntity != null && !isTokenExpired(authEntity.getTokenExpiration())) {
            return authEntity.getUsername(); // Token valide
        } else {
            return null; // Token expiré ou invalide
        }
    }

}

