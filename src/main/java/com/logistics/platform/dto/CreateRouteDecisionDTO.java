package com.logistics.platform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRouteDecisionDTO {

    @NotNull(message = "Route ID is required")
    private Integer routeId;

    private String decisionStatus;

    private BigDecimal riskScore;

    private Integer totalConstraintViolations;
    private Integer timeWindowViolations;
    private Integer capacityViolations;
    private Integer availabilityViolations;

    private String decisionReason;
    private String suggestedAction;

    private Integer approvedBy;
}