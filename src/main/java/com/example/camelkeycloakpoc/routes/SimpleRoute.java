package com.example.camelkeycloakpoc.routes;

import com.example.camelkeycloakpoc.security.JwtAuthFilter;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleRoute extends RouteBuilder {
    private final JwtAuthFilter jwtAuthFilter;

    public SimpleRoute(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("/api")
                .get("/secure")
                .to("direct:secureEndpoint");
        from("direct:secureEndpoint")
                .process(jwtAuthFilter)
                .setBody(simple("This is a secure endpoint"));
    }
}
