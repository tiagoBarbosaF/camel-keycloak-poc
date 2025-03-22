package com.example.camelkeycloakpoc.routes;

import com.example.camelkeycloakpoc.processor.CsvProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        CsvDataFormat csvDataFormat = new CsvDataFormat();
        csvDataFormat.setUseMaps("true");
        csvDataFormat.setSkipHeaderRecord("true");

        from("file:/Users/tiagobarbosa/Documents/dev/personal/test-docs?noop=true")
                .routeId("csv-route")
                .log("🔵 [CSV] Carregando arquivo csv...")
                .unmarshal(csvDataFormat)
                .process(new CsvProcessor())
                .log("🟢 [CSV] Dados: ${body}");
    }
}
