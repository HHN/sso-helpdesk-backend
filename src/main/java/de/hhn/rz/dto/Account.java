/*
 * Copyright © 2023 Hochschule Heilbronn (ticket@hs-heilbronn.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hhn.rz.dto;

import de.hhn.rz.MfaUtil;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public record Account(String keycloakId, String id, String username, String firstName, String lastName, String email,
                      String type, Long accountExpires, List<String> groups, boolean passwordUpdateRequired, boolean enabled) {

    public Account(UserRepresentation ua) {
        this(ua.getId(),
                Optional.ofNullable(ua.getAttributes()).map(o -> o.get("employeeID")).orElse(List.of("N/A")).get(0),
                ua.getUsername(),
                ua.getFirstName(),
                ua.getLastName(),
                ua.getEmail(),
                Optional.ofNullable(ua.getAttributes()).map(o -> o.get("type")).orElse(List.of("N/A")).get(0),
                Optional.ofNullable(ua.getAttributes()).map(o -> o.get("accountExpires")).map((o -> Long.parseLong(o.get(0)))).orElse(-1L),
                ua.getGroups(),
                ua.getRequiredActions().contains("UPDATE_PASSWORD"),
                ua.isEnabled()
        );
    }
}
