package com.imt.invocation_service.Controller;



import com.imt.invocation_service.InvoModel.ApiResponse;
import com.imt.invocation_service.InvoModel.Invocation;
import com.imt.invocation_service.Service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/invocations")
public class InvocationController {

    @Autowired
    private InvocationService invocationService;

    @PostMapping("/summon-monster")
    public ResponseEntity<ApiResponse> invokeMonster(@RequestHeader("Authorization") String token) {
        try {
            Integer monsterId = invocationService.invokeMonster(token);

            if (monsterId == null) {
                // Si l'ID du monstre est nul, une erreur est survenue
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Erreur lors de l'acquisition du monstre", false));
            }

            // si tout s'est bien passé, retourner l'ID du monstre et une réponse de succès
            return ResponseEntity.ok(new ApiResponse("Monstre invoqué avec succès ! (ID: " + monsterId + ")", true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de l'invocation du monstre : " + e.getMessage(), false));
        }
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



