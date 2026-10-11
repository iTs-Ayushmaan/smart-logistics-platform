package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateRouteDecisionDTO {

    private String decisionStatus;

    @DecimalMin(value = "0.0", message = "Risk score cannot be negative")
    private BigDecimal riskScore;

    @Min(value = 0, message = "Constraint violations cannot be negative")
    private Integer totalConstraintViolations;

    @Min(value = 0, message = "Time window violations cannot be negative")
    private Integer timeWindowViolations;

    @Min(value = 0, message = "Capacity violations cannot be negative")
    private Integer capacityViolations;

    @Min(value = 0, message = "Availability violations cannot be negative")
    private Integer availabilityViolations;

    private String decisionReason;

    private String suggestedAction;

    private Integer approvedBy;
}