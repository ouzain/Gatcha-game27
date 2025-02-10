package com.imt.invocation_service.CrudService;

import com.imt.invocation_service.InvoModel.Invocation;
import com.imt.invocation_service.InvoModel.Invocation;

import java.util.List;

public interface AddInvocation {
    public void execute(Invocation invocation);

    List<Invocation> getAllInvocations();

    Invocation invokeMonster(String token, String playerId);

    void recreateAllInvocations();
}
