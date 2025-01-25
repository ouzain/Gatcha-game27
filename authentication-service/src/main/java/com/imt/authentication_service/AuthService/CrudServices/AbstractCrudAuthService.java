package com.imt.authentication_service.AuthService.CrudServices;

import com.imt.authentication_service.Dao.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract class AbstractCrudAuthService {

    @Autowired
    protected AuthRepository authRepository;

}
