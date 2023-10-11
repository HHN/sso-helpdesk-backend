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
package de.hhn.rz.services;

import de.hhn.rz.AbstractService;
import de.hhn.rz.db.entities.AuditAction;
import de.hhn.rz.dto.Account;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class KeycloakService extends AbstractService {

    private static final Pattern PATTERN_EMPLOYEE_ID = Pattern.compile("^[MmPp][0-9]*$");
    private final RealmResource client;
    private final CredentialService credentialService;
    private final AuditLogService auditLogService;

    public KeycloakService(@Autowired RealmResource client,
                           @Autowired CredentialService credentialService,
                           @Autowired AuditLogService auditLogService) {
        this.client = client;
        this.credentialService = credentialService;
        this.auditLogService = auditLogService;
    }

    public List<Account> findAccounts(Integer first, Integer max, String searchParameter) {
        if (isEmployeeId(searchParameter)) {
            return client.users().searchByAttributes("employeeID:" + searchParameter).stream().map(Account::new).toList();
        }
        return client.users().search(searchParameter, first, max).stream().map(Account::new).toList();
    }

    public void resetCredentials(String keycloakId, String seq) {
        checkParameter(keycloakId);
        checkParameter(seq);

        final UserResource u = client.users().get(keycloakId);
        if (u == null) {
            throw new IllegalArgumentException("No user found. ID='" + keycloakId + "' is invalid?!");
        }
        auditLogService.audit(AuditAction.RESET_CREDENTIALS, "user=" + u.toRepresentation().getUsername(), "keycloak-id=" + keycloakId, "seq=" + seq);

        // Remove 2FA and any other non password thingy
        for (CredentialRepresentation cr : u.credentials()) {
            if (!CredentialRepresentation.PASSWORD.equals(cr.getType())) {
                u.removeCredential(cr.getId());
            }
        }

        // Reset the password for the given user
        u.resetPassword(credentialService.getCredentials(seq));

    }


    private boolean isEmployeeId(String id) {
        if (id == null) {
            return false;
        }
        return PATTERN_EMPLOYEE_ID.matcher(id).find();
    }
}
