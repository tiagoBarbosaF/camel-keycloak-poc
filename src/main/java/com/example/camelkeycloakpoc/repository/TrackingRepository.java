package com.example.camelkeycloakpoc.repository;

import com.example.camelkeycloakpoc.persistence.TrackingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackingRepository extends MongoRepository<TrackingEntity, UUID> {
}
