package com.logistics.platform.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDecisionResponseDTO {

    private Integer id;
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
    private LocalDateTime approvedAt;

    private LocalDateTime createdAt;
}