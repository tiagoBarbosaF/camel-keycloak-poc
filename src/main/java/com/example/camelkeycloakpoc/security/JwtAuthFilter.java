package com.example.camelkeycloakpoc.security;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            exchange.getIn().setHeader("userId", jwt.getSubject());
            exchange.getIn().setHeader("userRoles", jwt.getClaimAsStringList("realm_access"));
        } else {
            throw new SecurityException("Invalid JWT token");
        }
    }
}
