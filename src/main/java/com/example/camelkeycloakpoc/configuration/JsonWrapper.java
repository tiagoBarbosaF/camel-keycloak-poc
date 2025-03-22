package com.example.camelkeycloakpoc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonWrapper {

    public static String wrap(String type, Object data) throws Exception {
        Map<String, Object> response = new HashMap<>();

        response.put("data", data);
        response.put("type", type);
        response.put("timestamp", System.currentTimeMillis());

        return new ObjectMapper().writeValueAsString(response);
    }
}
