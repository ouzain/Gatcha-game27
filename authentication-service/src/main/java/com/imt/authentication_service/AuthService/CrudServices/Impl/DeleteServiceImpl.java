package com.imt.authentication_service.AuthService.CrudServices.Impl;

import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthService.CrudServices.AbstractCrudAuthService;
import com.imt.authentication_service.AuthService.CrudServices.DeleteService;
import org.springframework.stereotype.Service;

@Service(value = "DeleteUserService")
public class DeleteServiceImpl extends AbstractCrudAuthService implements DeleteService {

    @Override
    public void execute(String username){
        AuthEntity player = authRepository.findByUsername(username);
        //TODO: Verifier si le user n'est pas null
        authRepository.delete(player);
    }
}
