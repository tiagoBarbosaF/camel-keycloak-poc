package com.example.camelkeycloakpoc.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationInterceptor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public void process(Exchange exchange) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            exchange.getIn().setHeader("Authorization", "Bearer " + jwt.getTokenValue());

            String clientId = authentication.getName();
            Object clientName = jwt.getClaim("azp");
            logger.info("✅ Usuário autenticado: {}, Cliente: {}", clientId, clientName);
        } else {
            logger.warn("⚠️ Nenhum token JWT encontrado no contexto de segurança.");
        }
    }
}
