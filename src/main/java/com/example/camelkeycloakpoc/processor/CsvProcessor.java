package com.example.camelkeycloakpoc.processor;

import com.example.camelkeycloakpoc.configuration.JsonWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CsvProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        List<Map<String, String>> csvData = exchange.getIn().getBody(List.class);

        String jsonOutput = JsonWrapper.wrap("CSV_DATA", csvData);
        exchange.getIn().setBody(jsonOutput);
    }
}
