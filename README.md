
# Proyecto de Prueba OAuth2 con Spring Boot, Angular y Google Sign-In

Este proyecto de prueba demuestra la implementación de autenticación y autorización utilizando OAuth2 Server Authorization con generación de tokens, así como la integración con Google Sign-In. El proyecto utiliza las siguientes tecnologías:

- **Spring Boot**: Para implementar el servidor de autorización y el recurso.
- **OpenID Connect**: Para autenticación y autorización basada en identidad.
- **OAuth2 Resource Server**: Para proteger recursos y validar tokens.
- **Angular**: Para el frontend de la aplicación.
- **MySQL**: Para la persistencia de datos.

## Características

- Autenticación y autorización utilizando OAuth2.
- Generación de tokens JWT.
- Inicio de sesión con Google.
- Pantalla de consentimiento de permisos de Google.
- Persistencia en una base de datos MySQL.

## Requisitos

- Java 17 o superior.
- Maven 3.6 o superior.
- Node.js 18 o superior.
- MySQL 8 o superior.
- Google Developer Account (para configurar el inicio de sesión con Google).

## Configuración

### Configuración del Servidor Spring Boot

1. Clona el repositorio:

    ```bash
    git clone https://github.com/antruigon/oauth2-springboot-angular-googlesignin-poc.git
    cd oauth2-springboot-angular-googlesignin-poc
    ```

2. Configura las propiedades de la base de datos en `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    ```

3. Configura las propiedades de OAuth2 y Google Sign-In en `src/main/resources/application.properties`:

    ```properties
    spring.security.oauth2.client.registration.google.client-id=TU_CLIENT_ID
    spring.security.oauth2.client.registration.google.client-secret=TU_CLIENT_SECRET
    spring.security.oauth2.client.registration.google.scope=profile,email
    ```

4. Ejecuta la aplicación Spring Boot:
	1. Navega al directorio del Servidor de Autenticación:

    ```bash
    cd authorization-server
    ```
	2. Ejecuta el servidor con
	```bash
    ./mvn spring-boot:run
    ```

	3. Navega al directorio del Servidor de Recursos:

    ```bash
    cd resource-server
    ```
	4. Ejecuta el servidor con
	```bash
    ./mvnw spring-boot:run
    ```

### Configuración del Frontend Angular

1. Navega al directorio del frontend:

    ```bash
    cd ag-prueba-front
    ```

2. Instala las dependencias de Node.js:

    ```bash
    npm install
    ```
4. Ejecuta la aplicación Angular:

    ```bash
    ng serve
    ```

## Uso

1. Abre tu navegador y dirígete a `http://localhost:4200`.
2. Deberías ver un menú con la opción de inicio de sesión.
3. Al acceder, deberías ver un menú con opciones para iniciar sesión con Google.
4. Al iniciar sesión con Google, se mostrará la pantalla de consentimiento de permisos.
5. Una vez autenticado, serás redirigido a la aplicación con acceso basado en los permisos otorgados y verás en el menú una opción nueva.

La base de datos deberás de rellenarla. Hay endpoints para crear usuarios pero se puede rellenar manualmente. Los usuarios con el rol de "administrador" (ROLE_ADMIN) tendrán una opción más en el menú.


[![quality](https://github.com/IsraelHuro/oauth2-springboot-angular-googlesignin-poc/actions/workflows/quality.yml/badge.svg)](https://github.com/IsraelHuro/oauth2-springboot-angular-googlesignin-poc/actions/workflows/quality.yml)
