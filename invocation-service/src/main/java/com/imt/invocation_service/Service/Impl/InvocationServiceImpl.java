package com.imt.invocation_service.Service.Impl;


import com.imt.invocation_service.Dao.BaseMonsterRepository;
import com.imt.invocation_service.Dao.InvocationRepository;
import com.imt.invocation_service.InvoModel.BaseMonster;
import com.imt.invocation_service.InvoModel.Invocation;
import com.imt.invocation_service.Service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Random;

@Service(value = "InvocationService")
public class InvocationServiceImpl implements InvocationService {

    @Autowired
    private BaseMonsterRepository baseMonsterRepository;

    @Autowired
    private InvocationRepository invocationRepository;

    private final Random random = new Random();

    @Override
    public void execute(Invocation invocation) {

    }

    /**
     * Méthode principale pour invoquer un monstre
     * @param token Le token d'authentification
     * @param playerId L'ID du joueur
     * @return L'entité Invocation, avec info sur le monstre invoqué
     */
    @Override
    public Invocation invokeMonster(String token, String playerId) {
        // 1) Vérifier le token auprès de l'API Auth
        String usernameFromToken = checkAuthToken(token);
        if (usernameFromToken == null) {
            // Gérer l'erreur (token invalide) -> dans un vrai code, on ferait plutôt une exception 401
            throw new RuntimeException("Token invalide");
        }

        // 2) Sélectionner le monstre aléatoirement selon les taux
        BaseMonster baseMonster = pickRandomMonster();

        // 3) Créer un objet Invocation en base (statut PENDING)
        Invocation invocation = new Invocation();
        invocation.setPlayerId(playerId);
        invocation.setBaseMonsterId(baseMonster.getId().toString());
        invocation.setCreatedAt(LocalDateTime.now());
        invocation.setStatus("PENDING");
        invocation.addLog("Start invocation");
        invocation.addLog("Selected monster base: " + baseMonster.getName());

        invocationRepository.save(invocation);

        // 4) Appeler l’API Monstres pour créer l’instance de monstre
        String createdMonsterId = callMonstersApiToCreateMonster(baseMonster, playerId);
        invocation.setCreatedMonsterId(Integer.valueOf(createdMonsterId));
        invocation.addLog("Called Monstres API -> success with ID = " + createdMonsterId);

        // 5) Appeler l’API Joueur pour ajouter le monstre au joueur
        boolean addedToPlayer = callJoueurApiToAddMonster(playerId, createdMonsterId);
        if (!addedToPlayer) {
            invocation.addLog("Error adding monster to player " + playerId);
            invocation.setStatus("ERROR");
            invocationRepository.save(invocation);
            return invocation;
        }
        invocation.addLog("Added monster to player " + playerId + " -> success");

        // 6) Mettre à jour le statut final en base
        invocation.setStatus("FINISHED");
        invocation.addLog("Invocation finished");
        invocationRepository.save(invocation);

        return invocation;
    }

    /**
     * Algorithme de sélection d’un monstre basé sur le champ 'invocationRate'.
     */

    private BaseMonster pickRandomMonster() {
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
    }

    /**
     * Simule l'appel à l'API Auth pour valider le token
     */
    private String checkAuthToken(String token) {
        // Dans la réalité, vous feriez un appel REST vers l'API Auth
        // pour vérifier si le token est valide et récupérer le username ou playerId
        // Ex: RESTTemplate, WebClient, Feign, etc.
        // Retourne null si invalide, ou le username si valide.
        return "player123"; // pour l'exemple
    }

    /**
     * Simule l'appel à l'API Monstres pour créer un monstre et renvoyer son ID
     */
    private String callMonstersApiToCreateMonster(BaseMonster baseMonster, String playerId) {
        // Appel REST à l’API Monstres
        // On suppose que l'API Monstres nous renvoie un ID unique (String)
        return String.valueOf(random.nextInt(999999999)); // stub
    }

    /**
     * Simule l'appel à l'API Joueur pour ajouter le monstre à la liste du joueur
     */
    private boolean callJoueurApiToAddMonster(String playerId, String monsterId) {
        // Appel REST à l’API Joueur pour ajouter le monstre
        // Return true si OK, false si KO
        return true; // stub
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
