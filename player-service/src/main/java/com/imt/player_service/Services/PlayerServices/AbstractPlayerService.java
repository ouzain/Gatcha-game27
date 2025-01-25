package com.imt.player_service.Services.PlayerServices;


import com.imt.player_service.Dao.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract class AbstractPlayerService {

    @Autowired
    protected PlayerRepository playerRepository;

}
