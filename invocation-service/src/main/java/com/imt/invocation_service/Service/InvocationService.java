package com.imt.invocation_service.Service;

import com.imt.invocation_service.InvoModel.Invocation;

import java.util.List;

public interface InvocationService {

    List<Invocation> getAllInvocations();

   // Integer invokeMonster(String token);

    void recreateAllInvocations();
}
