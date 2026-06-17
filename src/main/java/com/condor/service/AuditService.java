package com.condor.service;

import com.condor.repository.AuditRepository;
import com.condor.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final AuditRepository auditRepository;
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void register(
            String eventType,
            String entityName,
            Long entityId,
            String description
    ) {
        auditRepository.saveAudit(
            SecurityUtils.getDeviceId(),
            eventType, entityName, entityId, description
        );
    }
}