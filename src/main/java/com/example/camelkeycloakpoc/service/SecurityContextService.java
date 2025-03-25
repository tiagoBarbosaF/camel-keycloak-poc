package com.example.camelkeycloakpoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SecurityContextService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityContextService.class);
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    public SecurityContextService(JwtDecoder jwtDecoder,
                                  JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    public void setSecurityContext(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            Collection<GrantedAuthority> authorities = jwtAuthenticationConverter.convert(jwt).getAuthorities();
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            logger.warn("⚠️ Token inválido: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            throw new RuntimeException("Token inválido ou expirado.", e);
        }
    }
}
