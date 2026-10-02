package com.logistics.platform.service;

import com.logistics.platform.entity.AuditLog;
import com.logistics.platform.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    public Optional<AuditLog> getAuditLogById(Integer id) {
        return auditLogRepository.findById(id);
    }

    public List<AuditLog> getAuditLogsByActorId(Integer actorId) {
        return auditLogRepository.findByActorId(actorId);
    }

    public List<AuditLog> getAuditLogsByEntity(String entityType, Integer entityId) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    public AuditLog createAuditLog(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    public AuditLog updateAuditLog(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    public void deleteAuditLog(Integer id) {
        auditLogRepository.deleteById(id);
    }
}