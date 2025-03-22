package com.example.camelkeycloakpoc.routes;

import com.example.camelkeycloakpoc.processor.PersonProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PersonRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("timer:fetchPerson?period=10000")
                .routeId("person-route")
                .log("🔵 [REST] Buscando lista de pessoas...")
                .to("http://localhost:8081/api/person/list?page=0&pageSize=10") // Chamada da api ocorre neste trecho
                .process(new PersonProcessor())
                .log("🟢 [REST] Lista de pessoas recebida: ${body}");
    }
}
