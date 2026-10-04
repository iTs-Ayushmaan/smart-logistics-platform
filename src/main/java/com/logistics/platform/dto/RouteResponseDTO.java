package com.logistics.platform.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponseDTO {

    private Integer id;
    private Integer driverId;
    private LocalDate routeDate;
    private String status;

    private BigDecimal totalDistanceKm;
    private Integer estimatedTimeMinutes;
    private BigDecimal totalCost;

    private Integer totalOrders;
    private Integer completedOrders;

    private String routeSequence;

    private String optimizedBy;
    private LocalDateTime optimizationTimestamp;
    private Integer optimizationDurationMs;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}