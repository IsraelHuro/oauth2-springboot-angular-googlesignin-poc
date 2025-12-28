package com.aghairsalon.authorizationserver.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ActuatorSecurityConfig {

    /**
     * OKR5: Permitimos acceso sin login a:
     *  - /actuator/health (estado UP/DOWN)
     *  - /actuator/prometheus (métricas para Prometheus/Grafana)
     *
     * Se define con @Order(0) para que se aplique antes que tus cadenas @Order(1) y @Order(2).
     */
    @Bean
    @Order(0)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // Esta cadena SOLO aplica a los endpoints Actuator indicados
            .securityMatcher(EndpointRequest.to("health", "prometheus"))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // Para endpoints de lectura no necesitamos CSRF
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
