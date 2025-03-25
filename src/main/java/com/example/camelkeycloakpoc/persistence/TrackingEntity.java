package com.example.camelkeycloakpoc.persistence;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Document(collection = "tracking")
public class TrackingEntity {
    @Id
    private final UUID id;
    private final String userId;
    private final String clientId;
    private final String routeId;
    private final Instant timestamp;
    private final Map<String, Object> additionalData;

    public TrackingEntity(String userId,
                          String clientId,
                          String routeId,
                          Instant timestamp,
                          Map<String, Object> additionalData) {
        this.id = UuidCreator.getTimeOrderedEpoch();
        this.userId = userId;
        this.clientId = clientId;
        this.routeId = routeId;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
        this.additionalData = additionalData != null ? additionalData : new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRouteId() {
        return routeId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }
}
