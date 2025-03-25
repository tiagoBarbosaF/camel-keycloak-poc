package com.example.camelkeycloakpoc.service;

import com.example.camelkeycloakpoc.persistence.TrackingEntity;
import com.example.camelkeycloakpoc.repository.TrackingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class TrackingService {
    private final TrackingRepository trackingRepository;

    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    public void saveTracking(Map<String, Object> trackingData) {
        TrackingEntity tracking = new TrackingEntity(
                (String) trackingData.get("userId"),
                (String) trackingData.get("clientId"),
                (String) trackingData.get("routeId"),
                Instant.ofEpochMilli((Long) trackingData.get("timestamp")),
                trackingData
        );
        trackingRepository.save(tracking);
    }
}
