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
import de.hhn.rz.db.AuditLogRepository;
import de.hhn.rz.db.entities.AuditAction;
import de.hhn.rz.db.entities.AuditLogEntry;
import org.apache.commons.lang3.ArrayUtils;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Service
public class AuditLogService extends AbstractService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(@Autowired AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void audit(AuditAction auditAction, String... params) {
        checkParameter(auditAction);
        final AuditLogEntry ale = new AuditLogEntry();
        ale.setAction(auditAction);

        params = ArrayUtils.insert(0, params, "ip=" + getPrincipalIp());

        ale.setParams(Arrays.toString(params));
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof KeycloakPrincipal<?> kp) {
            ale.setActor(kp.getKeycloakSecurityContext().getIdToken().getPreferredUsername());
        } else {
            ale.setActor(principal.toString());
        }
        auditLogRepository.save(ale);
    }

    private String getPrincipalIp() {

        final Object o = RequestContextHolder.currentRequestAttributes();

        if (o instanceof ServletRequestAttributes requestAttributes) {
            final String header = requestAttributes.getRequest().getHeader("X-Forwarded-For");
            if (header != null && !header.isBlank()) {
                return header;
            }
            return requestAttributes.getRequest().getRemoteAddr();
        }
        return "N/A";
    }

}
