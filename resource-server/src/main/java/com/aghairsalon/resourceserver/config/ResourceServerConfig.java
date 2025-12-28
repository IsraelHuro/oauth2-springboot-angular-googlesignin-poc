package com.aghairsalon.resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

    /**
     * Fix del error:
     * El Resource Server estaba intentando validar JWT usando "issuer-uri" (OIDC discovery),
     * pero el Authorization Server publica issuer como http://localhost:9000
     * y dentro de Docker se accede como http://authorization-server:9000 -> mismatch.
     *
     * Para evitarlo, usamos directamente el endpoint JWKs:
     *   {AUTH_SERVER_URL}/oauth2/jwks
     */
    @Bean
    public JwtDecoder jwtDecoder(@Value("${AUTH_SERVER_URL:http://localhost:9000}") String authServerUrl) {
        String base = authServerUrl.endsWith("/") ? authServerUrl.substring(0, authServerUrl.length() - 1) : authServerUrl;
        String jwkSetUri = base + "/oauth2/jwks";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // (Opcional útil) Deja los endpoints de actuator accesibles para monitorización
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health", "/actuator/prometheus", "/actuator/info").permitAll()
                .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder))
        );

        http.cors(Customizer.withDefaults());
        return http.build();
    }
}
