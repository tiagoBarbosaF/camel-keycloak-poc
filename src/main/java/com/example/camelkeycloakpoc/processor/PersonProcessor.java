package com.example.camelkeycloakpoc.processor;

import com.example.camelkeycloakpoc.configuration.JsonWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class PersonProcessor implements Processor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getIn().getBody(String.class);

        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode contentNode = rootNode.get("content");

        if (contentNode != null && contentNode.isArray()) {
            String restData = JsonWrapper.wrap("REST_DATA", contentNode);
            exchange.getIn().setBody(restData);
        } else {
            throw new RuntimeException("Formato inesperado de JSON.");
        }
    }
}
