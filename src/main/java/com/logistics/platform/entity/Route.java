package com.logistics.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "driver_id", nullable = false)
    private Integer driverId;

    @Column(name = "route_date", nullable = false)
    private LocalDate routeDate;

    @Column(length = 50)
    private String status = "PLANNED";

    @Column(name = "estimated_total_distance_km", precision = 10, scale = 2)
    private BigDecimal estimatedTotalDistanceKm;

    @Column(name = "actual_total_distance_km", precision = 10, scale = 2)
    private BigDecimal actualTotalDistanceKm;

    @Column(name = "estimated_total_time_minutes")
    private Integer estimatedTotalTimeMinutes;

    @Column(name = "actual_total_time_minutes")
    private Integer actualTotalTimeMinutes;

    @Column(name = "estimated_cost_rupees", precision = 10, scale = 2)
    private BigDecimal estimatedCostRupees;

    @Column(name = "actual_cost_rupees", precision = 10, scale = 2)
    private BigDecimal actualCostRupees;

    @Column(name = "total_orders")
    private Integer totalOrders;

    @Column(name = "completed_orders")
    private Integer completedOrders = 0;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "route_sequence", columnDefinition = "jsonb")
    private String routeSequence;

    @Column(name = "optimized_by", length = 50)
    private String optimizedBy = "GREEDY";

    @Column(name = "optimization_timestamp")
    private LocalDateTime optimizationTimestamp;

    @Column(name = "optimization_duration_seconds", precision = 10, scale = 3)
    private BigDecimal optimizationDurationSeconds;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}