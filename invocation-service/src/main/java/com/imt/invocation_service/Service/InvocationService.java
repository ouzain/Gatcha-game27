package com.imt.invocation_service.Service;

import com.imt.invocation_service.InvoModel.Invocation;

import java.util.List;

public interface InvocationService {
    public void execute(Invocation invocation);

    List<Invocation> getAllInvocations();

    Invocation invokeMonster(String token, String playerId);

    void recreateAllInvocations();
}
