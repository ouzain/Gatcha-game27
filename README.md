
## 📌 Description
Ce projet est une application Spring Boot, un jeu de type Gatcha qui permet aux joueurs de s'authentifier, invoquer des monstres,et les gérer.

Il repose sur une architecture microservices avec plusieurs APIs Restful :
- **API d'authentification** : Génération et gestion de tokens pour l'authentification.
- **API Joueur** : Gestion des informations du joueur, niveau et expérience.
- **API Monstres** : Gestion des monstres et de leurs statistiques.
- **API Invocations** : Invocation aléatoire de monstres.
- **API Combat** : Combat entre les différents Monstres.
- **Frontend** : Application Angular permettant de tester l'application.

---

## 🛠️ Technologies utilisées
- **Java 21**
- **Spring Boot**
- **Spring Data MongoDB**
- **OpenFeign**
- **Eureka Netflix Server**
- **REST API**
- **Docker**
- **MongoDB**
- **JUnit** (Tests unitaires)
- **Angular** (Pour le frontend)

---

## 🗺️ Architecture du Projet
Le projet est divisé en plusieurs services indépendants :
```
Gatcha-game27/
├── auth-service/         # API d'authentification
├── docker-dev-env/       # Répertoire Dockerisation
├── eureka-server/        # Serveur Eureka
├── gatcha-frontend/      # Interface utilisateur
├── invocation-service/   # API Invocations
├── monster-service/      # API Monstres
├── player-service/       # API Joueur
├── fight-service/        # API Combat         
└── README.md
```

---

## 🚀 Démarrer le Projet

### Prérequis
- **Docker**
- **Dans invcocation il y'a un monsters.json vous devez l'ajouter via swagger (localhost:8084) sur l'api invocation sur cet endpoint : /api/invocations/add/monsters**

### Lancer les services
1. **Cloner le projet :**
   ```bash
   git clone https://github.com/ouzain/Gatcha-game27.git
   cd Gatcha-game27/
   ```
2. **Lancer avec Docker Compose :**
   ```bash
   cd Gatcha-game27/docker-dev-env
   docker-compose up -d
   ```
3. **Vérifier que tous les service sont démarrés sur le serveur eureka:**
   - Server Eureka : `localhost:8761` 
4. **Accéder au frontend**
   - Frontend: `localhost:4200`
     
      NB: Au cas où un problème surviendrait dans le frontend vous pouvez tester les endpoints via swagger:
      - Auth Service : `localhost:8081/swagger-ui/index.html`
      - Player Service : `localhost:8082/swagger-ui/index.html`
      - Monster Service : `localhost:8083/swagger-ui/index.html`
      - Invocation Service : `localhost:8084/swagger-ui/index.html`
      - Fight Service : `localhost:8085/swagger-ui/index.html`
      

## 📊 Schéma des Interactions

![image](https://github.com/user-attachments/assets/fbd0ed19-8e72-4a6f-8f27-c78bb90f1ae9)




## 💡 Contributeurs



---





