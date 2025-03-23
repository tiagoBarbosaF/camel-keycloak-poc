package com.example.camelkeycloakpoc.routes;

import com.example.camelkeycloakpoc.processor.AuthenticationInterceptor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GlobalRouteConfiguration extends RouteBuilder {
    private final AuthenticationInterceptor authenticationInterceptor;

    public GlobalRouteConfiguration(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void configure() {
        interceptSendToEndpoint("http*://|rest://*")
                .process(authenticationInterceptor)
                .log("✅ Interceptor aplicado à requisição: ${headers}");

        log.info("🔹 GlobalRouteConfiguration carregado!");
    }
}
