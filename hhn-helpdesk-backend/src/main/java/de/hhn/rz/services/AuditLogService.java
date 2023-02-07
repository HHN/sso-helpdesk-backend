package de.hhn.rz.services;

import de.hhn.rz.AbstractService;
import de.hhn.rz.db.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService extends AbstractService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(@Autowired AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

}
