package com.logistics.platform.service;

import com.logistics.platform.dto.AuditLogResponseDTO;
import com.logistics.platform.dto.CreateAuditLogDTO;
import com.logistics.platform.dto.UpdateAuditLogDTO;
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

    // Get all audit logs
    public List<AuditLogResponseDTO> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get audit log by ID
    public Optional<AuditLogResponseDTO> getAuditLogById(Integer id) {
        return auditLogRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get audit logs by actor ID
    public List<AuditLogResponseDTO> getAuditLogsByActorId(Integer actorId) {
        return auditLogRepository.findByActorId(actorId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get audit logs by entity
    public List<AuditLogResponseDTO> getAuditLogsByEntity(
            String entityType, Integer entityId) {

        return auditLogRepository
                .findByEntityTypeAndEntityId(entityType, entityId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get audit logs by action
    public List<AuditLogResponseDTO> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Create audit log
    public AuditLogResponseDTO createAuditLog(CreateAuditLogDTO request) {

        AuditLog auditLog = new AuditLog();

        auditLog.setActorId(request.getActorId());
        auditLog.setEntityType(request.getEntityType());
        auditLog.setEntityId(request.getEntityId());
        auditLog.setAction(request.getAction());
        auditLog.setOldValue(request.getOldValue());
        auditLog.setNewValue(request.getNewValue());
        auditLog.setReason(request.getReason());

        AuditLog savedAuditLog = auditLogRepository.save(auditLog);

        return toResponseDTO(savedAuditLog);
    }

    // Update audit log
    public AuditLogResponseDTO updateAuditLog(
            Integer id, UpdateAuditLogDTO request) {

        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));

        if (request.getAction() != null) {
            auditLog.setAction(request.getAction());
        }

        if (request.getOldValue() != null) {
            auditLog.setOldValue(request.getOldValue());
        }

        if (request.getNewValue() != null) {
            auditLog.setNewValue(request.getNewValue());
        }

        if (request.getReason() != null) {
            auditLog.setReason(request.getReason());
        }

        AuditLog updatedAuditLog = auditLogRepository.save(auditLog);

        return toResponseDTO(updatedAuditLog);
    }

    // Delete audit log
    public void deleteAuditLog(Integer id) {
        auditLogRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private AuditLogResponseDTO toResponseDTO(AuditLog auditLog) {

        AuditLogResponseDTO dto = new AuditLogResponseDTO();

        dto.setId(auditLog.getId());
        dto.setActorId(auditLog.getActorId());
        dto.setEntityType(auditLog.getEntityType());
        dto.setEntityId(auditLog.getEntityId());
        dto.setAction(auditLog.getAction());
        dto.setOldValue(auditLog.getOldValue());
        dto.setNewValue(auditLog.getNewValue());
        dto.setReason(auditLog.getReason());
        dto.setTimestamp(auditLog.getTimestamp());

        return dto;
    }
}