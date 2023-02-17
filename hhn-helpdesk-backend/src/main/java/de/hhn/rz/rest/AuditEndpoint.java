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
package de.hhn.rz.rest;


import de.hhn.rz.AbstractService;
import de.hhn.rz.db.AuditLogRepository;
import de.hhn.rz.db.entities.AuditAction;
import de.hhn.rz.db.entities.AuditLogEntry;
import de.hhn.rz.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/admin/rest/")
public class AuditEndpoint extends AbstractService {

    private final AuditLogRepository service;
    private final AuditLogService auditLogService;

    public AuditEndpoint(@Autowired AuditLogRepository service, @Autowired AuditLogService auditLogService) {
        this.service = service;
        this.auditLogService = auditLogService;
    }

    @GetMapping("audits")
    public Page<AuditLogEntry> audits(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "50") int size) {
        auditLogService.audit(AuditAction.VIEW_AUDIT_LOG);

        if (page < 0) {
            page = 0;
        }

        if (size < 1 || size > 50) {
            size = 50;
        }

        return service.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

}
