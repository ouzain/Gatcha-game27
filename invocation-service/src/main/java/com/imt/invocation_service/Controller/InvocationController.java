package com.imt.invocation_service.Controller;


import com.imt.invocation_service.CrudService.AddInvocation;
import com.imt.invocation_service.InvoModel.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invocations")
public class InvocationController {

    @Autowired
    private AddInvocation invocationService;

    @PostMapping
    public Invocation invokeMonster(@RequestHeader("Authorization") String token,
                                    @RequestParam String playerId) {
        return invocationService.invokeMonster(token, playerId);
    }

    @GetMapping
    public List<Invocation> getAllInvocations() {
        return invocationService.getAllInvocations();
    }

    @PostMapping("/recreate")
    public void recreateInvocations() {
        invocationService.recreateAllInvocations();
    }
}


