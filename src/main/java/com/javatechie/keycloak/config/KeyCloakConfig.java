package com.javatechie.keycloak.config;

import com.javatechie.keycloak.converter.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class KeyCloakConfig {
    private final JwtAuthConverter jwtAuthConverter=new JwtAuthConverter();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt()
                                .jwtAuthenticationConverter(jwtAuthConverter)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login();

        return http.build();
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}