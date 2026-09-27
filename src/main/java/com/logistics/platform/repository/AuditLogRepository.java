package com.logistics.platform.repository;

import com.logistics.platform.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByActorId(Integer actorId);

    List<AuditLog> findByEntityTypeAndEntityId(String entityType, Integer entityId);

    List<AuditLog> findByAction(String action);
}