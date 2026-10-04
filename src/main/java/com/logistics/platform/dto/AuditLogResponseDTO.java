package com.logistics.platform.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponseDTO {

    private Integer id;

    private Integer actorId;

    private String entityType;
    private Integer entityId;

    private String action;

    private String oldValue;
    private String newValue;

    private String reason;

    private LocalDateTime timestamp;
}