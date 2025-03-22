package com.example.camelkeycloakpoc.routes;

import com.example.camelkeycloakpoc.processor.DatabaseProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MultiDatabaseRoute extends RouteBuilder {

    private final DatabaseProcessor databaseProcessor;

    public MultiDatabaseRoute(DatabaseProcessor databaseProcessor) {
        this.databaseProcessor = databaseProcessor;
    }

    @Override
    public void configure() throws Exception {
        from("timer:fetchClienteA?period=10000")
                .routeId("clienteA-route")
                .log("🔵 [JDBC] Selecionando a lista de pessoas...")
                .setBody(constant("SELECT * FROM person"))
                .to("jdbc:clienteADataSource")
                .process(databaseProcessor)
                .log("🟢 [JDBC] Dados: ${body}");
    }
}
