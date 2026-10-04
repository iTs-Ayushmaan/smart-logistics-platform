package com.logistics.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuditLogDTO {

    private Integer actorId;

    @NotBlank(message = "Entity type is required")
    private String entityType;

    private Integer entityId;

    @NotBlank(message = "Action is required")
    private String action;

    private String oldValue;

    private String newValue;

    private String reason;
}