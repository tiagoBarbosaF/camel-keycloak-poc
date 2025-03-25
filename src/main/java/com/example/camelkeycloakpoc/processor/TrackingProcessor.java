package com.example.camelkeycloakpoc.processor;

import com.example.camelkeycloakpoc.service.TrackingService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TrackingProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(TrackingProcessor.class);
    private final TrackingService trackingService;

    public TrackingProcessor(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = jwt.getSubject();
        String clientId = jwt.getClaim("azp").toString();
        String routeId = exchange.getFromRouteId();

        Map<String, Object> trackingData = new HashMap<>();
        trackingData.put("userId", userId);
        trackingData.put("clientId", clientId);
        trackingData.put("routeId", routeId);
        trackingData.put("timestamp", System.currentTimeMillis());

        logger.info("📍 [TRACKING] - {}", trackingData);

        trackingService.saveTracking(trackingData);
    }
}
