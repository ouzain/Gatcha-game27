package com.imt.invocation_service.Controller;



import com.imt.invocation_service.InvoModel.Invocation;
import com.imt.invocation_service.Service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/invocations")
public class InvocationController {

    @Autowired
    private InvocationService invocationService;

    @PostMapping
    public Invocation invokeMonster(@RequestHeader("Authorization") String token,
                                    @RequestParam String playerId) {
        // token contient le Bearer token
        // On l’envoie au service
        return invocationService.invokeMonster(token, playerId);
    }

    @GetMapping
    public List<Invocation> getAllInvocations() {
        // Retourne toutes les invocations enregistrées
        return invocationService.getAllInvocations();
    }

    @PostMapping("/recreate")
    public void recreateInvocations() {
        invocationService.recreateAllInvocations();
    }
}



