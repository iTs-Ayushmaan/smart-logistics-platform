package com.logistics.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "route_decisions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "route_id", nullable = false)
    private Integer routeId;

    @Column(name = "decision_status", nullable = false, length = 50)
    private String decisionStatus;

    @Column(name = "risk_score", precision = 3, scale = 2)
    private BigDecimal riskScore;

    @Column(name = "total_constraint_violations")
    private Integer totalConstraintViolations;

    @Column(name = "time_window_violations")
    private Integer timeWindowViolations;

    @Column(name = "capacity_violations")
    private Integer capacityViolations;

    @Column(name = "availability_violations")
    private Integer availabilityViolations;

    @Column(name = "decision_reason", columnDefinition = "TEXT")
    private String decisionReason;

    @Column(name = "suggested_action", length = 500)
    private String suggestedAction;

    @Column(name = "approved_by")
    private Integer approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}