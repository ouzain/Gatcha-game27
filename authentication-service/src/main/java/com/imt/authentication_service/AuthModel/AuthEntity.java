package com.imt.authentication_service.AuthModel;

import com.imt.authentication_service.Dto.AuthEntityDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "authentication")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthEntity {

    @Id
    private String id;
    @NotBlank
    private  String username;
    @NotBlank
    private  String password;
    private String token;
    private LocalDateTime tokenExpiration;

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

    public AuthEntityDto toAuthEntityDto(){
        AuthEntityDto authEntityDto= new AuthEntityDto();
        authEntityDto.setUsername(this.username);
        authEntityDto.setPassword(this.password);
        authEntityDto.setToken(this.token);
        authEntityDto.setTokenExpiration(this.tokenExpiration);

        return authEntityDto;
    }
}
