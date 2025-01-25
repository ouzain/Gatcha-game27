package com.imt.authentication_service.AuthService.CrudServices.Impl;

import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthService.CrudServices.AbstractCrudAuthService;
import com.imt.authentication_service.AuthService.CrudServices.AddService;
import org.springframework.stereotype.Service;

@Service(value = "AddUserService")
public class AddServiceImpl extends AbstractCrudAuthService implements AddService {

    @Override
    public void execute(AuthEntity player){
        this.authRepository.save(player);
    }
}
