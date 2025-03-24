package com.example.camelkeycloakpoc.routes;

import com.example.camelkeycloakpoc.processor.AuthenticationInterceptor;
import com.example.camelkeycloakpoc.processor.PersonProcessor;
import com.example.camelkeycloakpoc.processor.SecurityContextProcessor;
import com.example.camelkeycloakpoc.service.KeycloakTokenService;
import com.example.camelkeycloakpoc.service.SecurityContextService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final KeycloakTokenService keycloakTokenService;
    private final SecurityContextProcessor securityContextProcessor;
    private final SecurityContextService securityContextService;


    public RestRoute(AuthenticationInterceptor authenticationInterceptor,
                     KeycloakTokenService keycloakTokenService,
                     SecurityContextProcessor securityContextProcessor,
                     SecurityContextService securityContextService) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.keycloakTokenService = keycloakTokenService;
        this.securityContextProcessor = securityContextProcessor;
        this.securityContextService = securityContextService;
    }

    @Override
    public void configure() {
        from("timer:fetchPerson?period=10000")
                .routeId("person-route")
                .log("🔵 [REST] Buscando lista de pessoas...")
                .process(exchange -> {
                    String keycloakToken = keycloakTokenService.getAccessToken();
                    String invalidToken = keycloakToken + "X";
                    securityContextService.setSecurityContext(invalidToken);
                })
                .process(securityContextProcessor)
                .process(authenticationInterceptor)
                .to("http://localhost:8081/api/person/list?page=0&pageSize=10") // Chamada da api ocorre neste trecho
                .process(new PersonProcessor())
                .log("🟢 [REST] Lista de pessoas recebida: ${body}");
    }
}
