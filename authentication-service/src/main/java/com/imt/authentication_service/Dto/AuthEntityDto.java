package com.imt.authentication_service.Dto;

import com.imt.authentication_service.AuthModel.AuthEntity;

import java.time.LocalDateTime;

public class AuthEntityDto {

    private  String username;
    private  String password;
    private String token;
    private LocalDateTime tokenExpiration;

    public AuthEntityDto(){}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenExpiration(LocalDateTime tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getTokenExpiration() {
        return tokenExpiration;
    }

    public AuthEntity toAuthEntity(){
        AuthEntity authEntity= new AuthEntity();
        authEntity.setUsername(this.username);
        authEntity.setPassword(this.password);
        authEntity.setToken(this.token);
        authEntity.setTokenExpiration(this.tokenExpiration);

        return authEntity;
    }
}
