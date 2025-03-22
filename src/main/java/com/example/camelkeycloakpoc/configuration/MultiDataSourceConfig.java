package com.example.camelkeycloakpoc.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MultiDataSourceConfig {

    @Bean(name = "clienteADataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cliente-a")
    public DataSource clienteADataSource() {
        return new HikariDataSource();
    }
}
