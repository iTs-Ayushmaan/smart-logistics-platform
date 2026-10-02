package com.logistics.platform.controller;

import com.logistics.platform.entity.AuditLog;
import com.logistics.platform.service.AuditLogService;
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

    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> getAuditLogById(@PathVariable Integer id) {
        return auditLogService.getAuditLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/actor/{actorId}")
    public List<AuditLog> getAuditLogsByActorId(
            @PathVariable Integer actorId) {

        return auditLogService.getAuditLogsByActorId(actorId);
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public List<AuditLog> getAuditLogsByEntity(
            @PathVariable String entityType,
            @PathVariable Integer entityId) {

        return auditLogService.getAuditLogsByEntity(entityType, entityId);
    }

    @GetMapping("/action/{action}")
    public List<AuditLog> getAuditLogsByAction(
            @PathVariable String action) {

        return auditLogService.getAuditLogsByAction(action);
    }

    @PostMapping
    public AuditLog createAuditLog(@RequestBody AuditLog auditLog) {
        return auditLogService.createAuditLog(auditLog);
    }

    @PutMapping
    public AuditLog updateAuditLog(@RequestBody AuditLog auditLog) {
        return auditLogService.updateAuditLog(auditLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditLog(@PathVariable Integer id) {
        auditLogService.deleteAuditLog(id);
        return ResponseEntity.noContent().build();
    }
}