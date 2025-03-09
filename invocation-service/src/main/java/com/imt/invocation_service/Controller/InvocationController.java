package com.imt.invocation_service.Controller;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imt.invocation_service.Dto.MonsterDto;
import com.imt.invocation_service.InvoModel.ApiResponse;
import com.imt.invocation_service.InvoModel.Invocation;
import com.imt.invocation_service.OpenFeign.AuthServiceClient;
import com.imt.invocation_service.OpenFeign.MonsterServiceClient;
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

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private MonsterServiceClient monsterClient;

    @PostMapping("/summon-monster")
    public ResponseEntity<ApiResponse> invokeMonster(@RequestHeader("Authorization") String token) {
        try {
            // Vérifier le token auprès de l'API d'authentification
            String playerUsername = checkAuthToken(token);  // Appel à l'API d'authentification

            if (playerUsername == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse("Token invalide", false));
            }

            // Appeler l'API Monstres pour récupérer un monstre aléatoire
            MonsterDto monsterDto = fetchMonsterFromMonsterAPI();
            if (monsterDto == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Erreur lors de la récupération du monstre", false));
            }

            return ResponseEntity.ok(new ApiResponse("Monstre invoqué avec succès ! (ID: " + monsterDto.getId() + ")", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors de l'invocation du monstre : " + e.getMessage(), false));
        }
    }

    // Vérifier la validité du token via l'API Auth
    private String checkAuthToken(String token) {
        try {
            ResponseEntity<String> response = authServiceClient.validateToken("Bearer " + token);
            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());

                // extraire le username du champ  "data"
                return jsonResponse.get("data").asText();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Appel à l'API Monstres pour récupérer un monstre aléatoire
    private MonsterDto fetchMonsterFromMonsterAPI() {
        try {
            ResponseEntity<ApiResponse> response = monsterClient.generateRandomMonster();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody().isSuccess()) {
                return (MonsterDto) response.getBody().getData();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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



