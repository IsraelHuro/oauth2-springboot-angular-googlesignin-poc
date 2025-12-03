# Build Angular
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Servir estático con Nginx
FROM nginx:alpine
# OJO: ajusta esta ruta si tu dist tiene otro nombre
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
