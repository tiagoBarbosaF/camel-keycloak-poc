package com.example.camelkeycloakpoc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
        logger.info("Carregando SecurityConfig...");
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/secure")
                                .hasRole("user")
                                .anyRequest()
                                .authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
            jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");

            Collection<GrantedAuthority> authorities = new ArrayList<>(jwtGrantedAuthoritiesConverter.convert(jwt));

            Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
            logger.info("JWT claims: {}", jwt.getClaims()); // Log para verificar os claims disponíveis

            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                List<String> roles = (List<String>) realmAccess.get("roles");
                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
            }

            logger.info("Authorities extraídas do JWT: {}", authorities);

            return authorities;
        });

        return converter;
    }
}
