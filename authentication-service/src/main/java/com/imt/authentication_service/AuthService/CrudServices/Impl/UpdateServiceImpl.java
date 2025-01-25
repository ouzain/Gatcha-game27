package com.imt.authentication_service.AuthService.CrudServices.Impl;

import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthService.CrudServices.AbstractCrudAuthService;
import com.imt.authentication_service.AuthService.CrudServices.UpdateService;
import org.springframework.stereotype.Service;

@Service(value = "UpdateUserService")
public class UpdateServiceImpl extends AbstractCrudAuthService implements UpdateService {

    @Override
    public void execute(AuthEntity player) {
        AuthEntity foundPlayer = authRepository.findByUsername(player.getUsername());
        //TODO: ajouter un attribut uuid pour pouvoir rechercher un player par son uuid, car on peut bien vouloir modifier le username
        if (foundPlayer == null) {
            throw new RuntimeException("User not found: " + player.getUsername());
        }
        AuthEntity updatedPlayer = new AuthEntity();
        updatedPlayer.setUsername(player.getUsername() != null ? player.getUsername() : foundPlayer.getUsername());
        updatedPlayer.setPassword(player.getPassword() != null ? player.getPassword() : foundPlayer.getPassword());
        updatedPlayer.setToken(player.getToken() != null ? player.getToken() : foundPlayer.getToken());
        updatedPlayer.setTokenExpiration(player.getTokenExpiration() != null
                ? player.getTokenExpiration()
                : foundPlayer.getTokenExpiration());

        authRepository.save(updatedPlayer);

    }

}
