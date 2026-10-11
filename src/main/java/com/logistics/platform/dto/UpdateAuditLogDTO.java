package com.logistics.platform.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAuditLogDTO {

    @Size(max = 50, message = "Action must not exceed 50 characters")
    private String action;

    private String oldValue;

    private String newValue;

    private String reason;
}