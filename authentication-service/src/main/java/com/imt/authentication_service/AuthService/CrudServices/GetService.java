package com.imt.authentication_service.AuthService.CrudServices;

import com.imt.authentication_service.AuthModel.AuthEntity;

public interface GetService {
     AuthEntity byUserName(String  username);

     AuthEntity byToken(String token);
}
