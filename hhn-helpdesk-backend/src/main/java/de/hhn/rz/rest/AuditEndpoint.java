package de.hhn.rz.rest;


import de.hhn.rz.AbstractService;
import de.hhn.rz.db.AuditLogRepository;
import de.hhn.rz.db.entities.AuditAction;
import de.hhn.rz.db.entities.AuditLogEntry;
import de.hhn.rz.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<AuditLogEntry> audits() {
        auditLogService.audit(AuditAction.VIEW_AUDIT_LOG);
        return service.getAudits();
    }

}
