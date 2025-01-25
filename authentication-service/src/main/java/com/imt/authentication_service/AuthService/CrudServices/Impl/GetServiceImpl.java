package com.imt.authentication_service.AuthService.CrudServices.Impl;

import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthService.CrudServices.AbstractCrudAuthService;
import com.imt.authentication_service.AuthService.CrudServices.GetService;
import org.springframework.stereotype.Service;

@Service(value = "GetUserService")
public class GetServiceImpl extends AbstractCrudAuthService implements GetService {

    @Override
    public AuthEntity byUserName(String username){

        //TODO : Faire le check au cas où le User serait vide ie Null
        return this.authRepository.findByUsername(username);
    }

    @Override

    public AuthEntity byToken(String token){
        //TODO : Faire le check au cas où le User serait vide ie Null
        return this.authRepository.findByToken(token);
    }
}
