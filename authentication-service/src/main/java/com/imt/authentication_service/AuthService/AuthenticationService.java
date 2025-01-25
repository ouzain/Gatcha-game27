package com.imt.authentication_service.AuthService;

public interface AuthenticationService {
    String authenticate(String username, String password);

     String validateToken(String token);
}
