package com.example.camelkeycloakpoc.processor;

import com.example.camelkeycloakpoc.configuration.JsonWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DatabaseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        List<Map<String, Object>> rows = exchange.getIn().getBody(List.class);

        if (rows == null || rows.isEmpty()) {
            exchange.getIn().setBody("Nenhum dado encontrado.");
            return;
        }

        String jsonResult = JsonWrapper.wrap("JDBC_DATA", rows);
        exchange.getIn().setBody(jsonResult);
    }
}
