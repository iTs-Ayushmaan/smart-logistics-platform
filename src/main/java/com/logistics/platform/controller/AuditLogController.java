package com.logistics.platform.controller;

import com.logistics.platform.dto.AuditLogResponseDTO;
import com.logistics.platform.dto.CreateAuditLogDTO;
import com.logistics.platform.dto.UpdateAuditLogDTO;
import com.logistics.platform.service.AuditLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // Get all audit logs
    @GetMapping
    public ResponseEntity<List<AuditLogResponseDTO>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogService.getAllAuditLogs());
    }

    // Get audit log by ID
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> getAuditLogById(
            @PathVariable Integer id) {

        return auditLogService.getAuditLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get audit logs by actor ID
    @GetMapping("/actor/{actorId}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByActorId(
            @PathVariable Integer actorId) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByActorId(actorId)
        );
    }

    // Get audit logs by entity
    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByEntity(
            @PathVariable String entityType,
            @PathVariable Integer entityId) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByEntity(entityType, entityId)
        );
    }

    // Get audit logs by action
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByAction(
            @PathVariable String action) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByAction(action)
        );
    }

    // Create audit log
    @PostMapping
    public ResponseEntity<AuditLogResponseDTO> createAuditLog(
            @Valid @RequestBody CreateAuditLogDTO request) {

        return ResponseEntity.ok(
                auditLogService.createAuditLog(request)
        );
    }

    // Update audit log
    @PutMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> updateAuditLog(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateAuditLogDTO request) {

        return ResponseEntity.ok(
                auditLogService.updateAuditLog(id, request)
        );
    }

    // Delete audit log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditLog(
            @PathVariable Integer id) {

        auditLogService.deleteAuditLog(id);

        return ResponseEntity.noContent().build();
    }
}