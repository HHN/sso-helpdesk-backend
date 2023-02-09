package de.hhn.rz.services;

import de.hhn.rz.AbstractService;
import de.hhn.rz.db.AuditLogRepository;
import de.hhn.rz.db.entities.AuditAction;
import de.hhn.rz.db.entities.AuditLogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        ale.setParams(Arrays.toString(params));
        ale.setActor(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

}
