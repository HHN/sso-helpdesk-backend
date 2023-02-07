package de.hhn.rz.dto;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public record Account(String keycloakId, String id, String username, String firstName, String lastName, String email,
                      String type) {

    public Account(UserRepresentation ua) {
        this(ua.getId(),
                ua.getAttributes().getOrDefault("employeeID", List.of("N/A")).get(0),
                ua.getUsername(),
                ua.getFirstName(),
                ua.getLastName(),
                ua.getEmail(),
                ua.getAttributes().getOrDefault("type", List.of("N/A")).get(0));
    }
}
