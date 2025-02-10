package com.imt.invocation_service.CrudService.Impl;

import com.imt.invocation_service.InvoModel.BaseMonster;
import com.imt.invocation_service.InvoModel.Monster;
import com.imt.invocation_service.InvoModel.Invocation;
import com.imt.invocation_service.Dao.InvocationRepository;
import com.imt.invocation_service.Dao.MonsterRepository;
import com.imt.invocation_service.Dao.BaseMonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service(value = "addInvocation")
public class AddInvocationimpl {

        @Autowired
        private MonsterRepository monsterRepository;

        @Autowired
        private BaseMonsterRepository baseMonsterRepository;

        @Autowired
        private InvocationRepository invocationRepository;

        private final Random random = new Random();

        /**
         * Méthode principale pour invoquer un monstre
         * @param token Le token d'authentification
         * @param playerId L'ID du joueur
         * @return L'entité Invocation, avec info sur le monstre invoqué
         */
        public Invocation invokeMonster(String token, String playerId) {
            String usernameFromToken = checkAuthToken(token);
            if (usernameFromToken == null) {
                throw new RuntimeException("Token invalide");
            }


            BaseMonster baseMonster = pickRandomMonster();


            Invocation invocation = new Invocation();
            invocation.setPlayerId(playerId);
            invocation.setBaseMonsterId(baseMonster.getId());
            invocation.setCreatedAt(LocalDateTime.now());
            invocation.setStatus("PENDING");
            invocation.addLog("Start invocation");
            invocation.addLog("Selected monster base: " + baseMonster.getName());

            invocationRepository.save(invocation);


            String createdMonsterId = callMonstersApiToCreateMonster(baseMonster, playerId);
            invocation.setCreatedMonsterId(Integer.valueOf(createdMonsterId));
            invocation.addLog("Called Monstres API -> success with ID = " + createdMonsterId);


            boolean addedToPlayer = callJoueurApiToAddMonster(playerId, createdMonsterId);
            if (!addedToPlayer) {
                invocation.addLog("Error adding monster to player " + playerId);
                invocation.setStatus("ERROR");
                invocationRepository.save(invocation);
                return invocation;
            }
            invocation.addLog("Added monster to player " + playerId + " -> success");

            invocation.setStatus("FINISHED");
            invocation.addLog("Invocation finished");
            invocationRepository.save(invocation);

            return invocation;
        }


        private BaseMonster pickRandomMonster() {
            List<BaseMonster> allBaseMonsters = baseMonsterRepository.findAll();
            if (allBaseMonsters.isEmpty()) {
                throw new RuntimeException("No base monsters in database");
            }

            double totalRate = allBaseMonsters.stream()
                    .mapToDouble(BaseMonster::getInvocationRate)
                    .sum();


            double rand = random.nextDouble() * totalRate;

            double currentSum = 0.0;
            for (BaseMonster bm : allBaseMonsters) {
                currentSum += bm.getInvocationRate();
                if (rand <= currentSum) {
                    return bm;
                }
            }

            return allBaseMonsters.get(allBaseMonsters.size() - 1);
        }

        private String checkAuthToken(String token) {

            return "player123";
        }

        private String callMonstersApiToCreateMonster(BaseMonster baseMonster, String playerId) {
            return String.valueOf(random.nextInt(999999999));
        }
        private boolean callJoueurApiToAddMonster(String playerId, String monsterId) {
            return true;
        }

        public void recreateAllInvocations() {

            List<Invocation> allInvocations = invocationRepository.findAll();
            for (Invocation inv : allInvocations) {
            }
        }
    }

