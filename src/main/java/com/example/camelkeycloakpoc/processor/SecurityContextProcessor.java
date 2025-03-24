package com.example.camelkeycloakpoc.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(SecurityContextProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String token = exchange.getIn().getHeader("Authorization", String.class);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            Jwt jwt = Jwt.withTokenValue(token)
                    .header("alg", "RS256")
                    .claim("type", "Bearer")
                    .build();

            JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("✅SecurityContext atualizado com o jwt token!!!");
        } else {
            logger.warn("⚠️Nenhum token jwt no cabeçalho da requisição...");
        }
    }
}
