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
# COPY /dist/demo/browser /usr/share/nginx/html


