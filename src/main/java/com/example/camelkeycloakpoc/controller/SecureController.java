package com.example.camelkeycloakpoc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecureController {

    @GetMapping("/secure")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<String> secureEndpoint() {
        return ResponseEntity.ok("Access granted.");
    }
}
