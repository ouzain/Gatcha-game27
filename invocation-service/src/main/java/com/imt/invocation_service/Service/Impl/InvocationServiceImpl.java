package com.imt.invocation_service.Service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.imt.invocation_service.Dao.BaseMonsterRepository;
import com.imt.invocation_service.Dao.InvocationRepository;
import com.imt.invocation_service.Dto.MonsterDto;
import com.imt.invocation_service.InvoModel.ApiResponse;
import com.imt.invocation_service.InvoModel.BaseMonster;
import com.imt.invocation_service.InvoModel.Invocation;
import com.imt.invocation_service.OpenFeign.AuthServiceClient;
import com.imt.invocation_service.OpenFeign.MonsterServiceClient;
import com.imt.invocation_service.OpenFeign.PlayerClient;
import com.imt.invocation_service.Service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Random;

@Service(value = "InvocationService")
public class InvocationServiceImpl implements InvocationService {

    @Autowired
    private BaseMonsterRepository baseMonsterRepository;

    @Autowired
    private InvocationRepository invocationRepository;
    @Autowired
    private MonsterServiceClient monsterClient; //
    @Autowired
    private AuthServiceClient authServiceClient;
    @Autowired
    private PlayerClient playerClient;

    private final Random random = new Random();


    /**
     * Méthode principale pour invoquer un monstre
     * @param token Le token d'authentification
     * @return L'entité Invocation, avec info sur le monstre invoqué
     */
    @Override
    public Integer invokeMonster(String token) {
        // vérifier le token auprès de api auth et récupérer le username associé
        String playerUsername = checkAuthToken(token).getBody();
        if (playerUsername == null) {
            throw new RuntimeException("Token invalide");
        }

        // Appeler l'API Monstre pour créer un monstre
        MonsterDto monsterDto = fetchMonsterFromMonsterAPI();  // Appel à l'API Monstres via Feign
        if (monsterDto == null) {
            throw new RuntimeException("Erreur lors de la récupération du monstre");
        }

        // Retourner l'ID du monstre créé
        return monsterDto.getId(); // On retourne l'ID du monstre créé
    }


    //  appeler l'api monstre pour récupérer un monstre
    private MonsterDto fetchMonsterFromMonsterAPI() {
        try {
            // Appel de l'API Monstres via Feign pour invoquer un monstre aléatoire
            ResponseEntity<ApiResponse> response = monsterClient.generateRandomMonster();
            if (response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccess()) {
                return (MonsterDto) response.getBody().getData();
            } else {
                throw new RuntimeException("echec de recuperation d'un à partir de l'API Monstres");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Algorithme de sélection d’un monstre basé sur le champ 'invocationRate'.
     */

   /* private BaseMonster pickRandomMonster() {
        List<BaseMonster> allBaseMonsters = baseMonsterRepository.findAll();
        if (allBaseMonsters.isEmpty()) {
            throw new RuntimeException("No base monsters in database");
        }

        // Somme de tous les taux
        double totalRate = allBaseMonsters.stream()
                .mapToDouble(BaseMonster::getInvocationRate)
                .sum();

        // Génération d'un nombre aléatoire entre 0 et totalRate
        double rand = random.nextDouble() * totalRate;

        double currentSum = 0.0;
        for (BaseMonster bm : allBaseMonsters) {
            currentSum += bm.getInvocationRate();
            if (rand <= currentSum) {
                return bm;
            }
        }

        // Par sécurité, on retourne le dernier si jamais l'arrondi cause un souci
        return allBaseMonsters.get(allBaseMonsters.size() - 1);
    }*/

    /**
     * appel à l'API Auth pour valider le token
     */


//    private String checkAuthToken(String token) {
//        try {
//
//            String authorizationHeader = "Bearer " + token;
//
//            // appeler l'API d'auth pour vérifier le token
//            ResponseEntity<ApiResponse> response = authServiceClient.validateToken(authorizationHeader);
//
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().isSuccess()) {
//
//                return (String) response.getBody().getData(); // Le username est stocké dans 'data'
//            } else {
//                // Token invalide
//                return null;
//            }
//        } catch (Exception e) {
//            //TODO Gérer les erreurs (ex: échec de la connexion à l'API d'authentification)
//            e.printStackTrace();
//            return null;
//        }
//    }


    private ResponseEntity<String> checkAuthToken(String token) {
        ResponseEntity<String> response = authServiceClient.validateToken("Bearer " + token);

        if(!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(response.getStatusCode())
                    .body(new RuntimeException("Erreur lors de la validation du token : " + response.getBody()).getMessage());
        }


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            // extraire le username du champ  "data"
            String username = jsonResponse.get("data").asText();
            return ResponseEntity.ok(username);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Exemple de méthode pour relancer/recréer toutes les invocations
     * (ici très simplifié)
     */
    @Override
    public void recreateAllInvocations() {
        // Logique : parcourir toutes les invocations stockées,
        // et rejouer/appliquer la même séquence ou logique business
        // en cas d'invocations incomplètes ou devant être restaurées.

        List<Invocation> allInvocations = invocationRepository.findAll();
        for (Invocation inv : allInvocations) {
            // Ex : si inv.status = ERROR, on retente, etc.
            // Cette logique dépend de vos besoins métiers.
        }
    }

    @Override
    public List<Invocation> getAllInvocations() {
        return invocationRepository.findAll();
    }

}
