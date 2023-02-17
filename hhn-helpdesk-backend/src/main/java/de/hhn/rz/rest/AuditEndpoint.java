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
import org.springframework.lang.Nullable;
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
