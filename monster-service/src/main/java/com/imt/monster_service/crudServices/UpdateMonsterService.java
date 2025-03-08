package com.imt.monster_service.crudServices;

import com.imt.monster_service.Model.Monster;

public interface UpdateMonsterService {
    public void execute(Monster monster);
    public void addExp(Integer id, int exp);

}
