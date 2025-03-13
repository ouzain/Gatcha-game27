### STAGE 1: Build ###
FROM node:18.20.7-alpine AS build
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build 
### STAGE 2: Run ###
FROM nginx:1.27.4-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/demo/browser /usr/share/nginx/html


# FROM nginx:1.27.4-alpine

# COPY nginx.conf /etc/nginx/nginx.conf
# COPY /demo/browser /usr/share/nginx/html


# # Répertoire de travail
# WORKDIR /app

# # Copier les fichiers package.json et package-lock.json
# COPY package*.json ./

# # Installer les dépendances
# RUN npm install

# # Copier le reste des fichiers du projet
# COPY . .

# # Construire l'application pour la production
# RUN npm run build --prod

# # Étape 2 : Configuration du serveur Nginx pour servir l'application
# FROM nginx:alpine

# # Copier les fichiers de build depuis l'étape précédente
# COPY --from=build /app/dist/ /usr/share/nginx/html

# # Exposer le port 4200
# EXPOSE 4200

# # Commande pour démarrer Nginx
# CMD ["nginx", "-g", "daemon off;"]
